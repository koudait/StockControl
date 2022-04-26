

package com.mobero.stockcontrol

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobero.stockcontrol.result.ScanResult

class ScanActivityViewModel: ViewModel() {
    val resultLiveData: MutableLiveData<List<ScanResult>> = MutableLiveData(emptyList())

    fun add(result: ScanResult): Boolean {
        val results = resultLiveData.value ?: emptyList()
        if (results.contains(result)) return false
        resultLiveData.value = results + result
        return true
    }
}
