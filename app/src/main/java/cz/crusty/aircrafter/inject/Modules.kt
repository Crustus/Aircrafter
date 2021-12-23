package cz.crusty.aircrafter.inject

import cz.crusty.aircrafter.repository.remote.API
import cz.crusty.aircrafter.ui.dashboard.StatesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { API() }

    viewModel { StatesViewModel(get()) }


}