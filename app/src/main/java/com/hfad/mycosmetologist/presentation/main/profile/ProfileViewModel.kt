package com.hfad.mycosmetologist.presentation.main.profile

import androidx.lifecycle.ViewModel
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getActualWorker: GetActualWorker,
    private val getPriceList: GetPriceList
): ViewModel() {
    private val workerIdFlow = getActualWorker()
        .mapNotNull { (it as? Result.Success)?.data}


    private val priceListFlow = workerIdFlow.flatMapLatest { getPriceList(it.id) }


}
