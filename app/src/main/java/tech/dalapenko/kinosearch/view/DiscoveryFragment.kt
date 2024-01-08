package tech.dalapenko.kinosearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import tech.dalapenko.kinosearch.R
import tech.dalapenko.kinosearch.databinding.DiscoveryFragmentBinding
import tech.dalapenko.feature.premieres.view.PremieresFragment
import tech.dalapenko.feature.releases.view.ReleasesFragment

class DiscoveryFragment : Fragment(R.layout.discovery_fragment) {

    private var _binding: DiscoveryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DiscoveryFragmentBinding.inflate(inflater, container, false)
        configTabLayout()
        configSearchLayout()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configTabLayout() {
        val (tabLayout, viewPager) = with(binding) { discoveryTabs to discoveryPager }
        viewPager.adapter = TabsViewAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.setText(R.string.cinema_releases)
                1 -> tab.setText(R.string.digital_releases)
            }
        }.attach()
    }

    private fun configSearchLayout() {
        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_open_search)
        }
    }

    private class TabsViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> PremieresFragment()
                else -> ReleasesFragment()
            }
        }
    }
}