

package net.mm2d.codereader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mm2d.codereader.result.ScanResult

class ScanActivityViewModel: ViewModel() {
    val resultLiveData: MutableLiveData<List<ScanResult>> = MutableLiveData(emptyList())

    fun add(result: ScanResult): Boolean {
        val results = resultLiveData.value ?: emptyList()
        if (results.contains(result)) return false
        resultLiveData.value = results + result
        return true
    }
}
