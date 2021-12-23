package cz.crusty.aircrafter.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import cz.crusty.aircrafter.R
import cz.crusty.aircrafter.base.BaseFragment
import cz.crusty.common.util.flowWhenResumed

class DashboardFragment : BaseFragment() {

    private lateinit var viewModel: StatesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(StatesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        viewModel.apply {

            flowWhenResumed {
                /*pokemon.collect { value ->
                    //logThread("Pokemon ${value?.count}")
                    value?.results?.let { pokemonAdapter.add(it) }
                    setSubtitle("${pokemonAdapter.itemCount} / ${value?.count ?: "0"}")
                }*/
            }
        }

        root.apply {

        }

        return root
    }

}