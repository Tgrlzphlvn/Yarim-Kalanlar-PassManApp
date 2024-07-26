package com.ozpehlivantugrul.passmanapp.utils

import android.app.assist.AssistStructure
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import androidx.annotation.RequiresApi
import com.ozpehlivantugrul.passmanapp.room.UserRepository
import com.ozpehlivantugrul.passmanapp.viewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class PassAutofillService @Inject constructor(
    private val repository: UserRepository,
    private val viewModel: HomeViewModel
) : AutofillService() {

    private val context = applicationContext

    override fun onFillRequest(request: FillRequest, cancellationSignal: CancellationSignal, callback: FillCallback) {
        val fillContexts = request.fillContexts
        val structure = fillContexts.last().structure
        val autofillIds = parseStructure(structure)

        val url = "https://forum.donanimarsivi.com/"
        CoroutineScope(Dispatchers.IO).launch {
            val credential = repository.getCredentialByUrl(url)

            if (credential != null) {
                val dataset = autofillIds["username"]?.let {
                    autofillIds["password"]?.let { it1 ->
                        Dataset.Builder()
                            .setValue(it, AutofillValue.forText(viewModel.decryptItem(credential.username.iv, credential.username.hash, context)))
                            .setValue(it1, AutofillValue.forText(viewModel.decryptItem(credential.password.iv, credential.password.hash, context)))
                            .build()
                    }
                }

                val response = FillResponse.Builder()
                    .addDataset(dataset)
                    .build()

                callback.onSuccess(response)
            } else {
                callback.onFailure("No credentials found")
            }
        }
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        // Save logic
    }

    private fun parseStructure(structure: AssistStructure): Map<String, AutofillId> {
        val autofillIds = mutableMapOf<String, AutofillId>()

        for (i in 0 until structure.windowNodeCount) {
            val node = structure.getWindowNodeAt(i).rootViewNode
            traverseNode(node, autofillIds)
        }

        return autofillIds
    }

    private fun traverseNode(node: AssistStructure.ViewNode, autofillIds: MutableMap<String, AutofillId>) {
        if (node.className?.contains("EditText") == true) {
            if (node.hint?.contains("username", ignoreCase = true) == true) {
                autofillIds["username"] = node.autofillId!!
            } else if (node.hint?.contains("password", ignoreCase = true) == true) {
                autofillIds["password"] = node.autofillId!!
            }
        }

        for (i in 0 until node.childCount) {
            traverseNode(node.getChildAt(i), autofillIds)
        }
    }
}

