package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.System.console
import java.lang.System.exit

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false);
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        val homeViewModel =
//            ViewModelProvider(this)[HomeViewModel::class.java]
        val imageView: ImageView = binding.imageView
        imageView.setOnClickListener { Toast.makeText(context, "" + homeViewModel.changeLocation(), Toast.LENGTH_SHORT).show()}
        binding.screen.setOnClickListener{
            homeViewModel.startGame()
        }
        homeViewModel.imageX.observe(viewLifecycleOwner) {
            imageView.setX(it)
        }
        homeViewModel.imageY.observe(viewLifecycleOwner) {
            imageView.setY(it)
        }
        homeViewModel.imageSrc.observe(viewLifecycleOwner) {
            imageView.setImageResource(it)
        }
        homeViewModel.imageVsb.observe(viewLifecycleOwner) {
            if(it){
                imageView.setVisibility(View.VISIBLE)
            }else{
                imageView.setVisibility(View.GONE)
            }
        }
        homeViewModel.tryOne.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.tryOne.text = getString(R.string.try_one, it)
            }
            else{
            binding.tryOne.text = ""
        }
        }
        homeViewModel.tryTwo.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.tryTwo.text = getString(R.string.try_two, it)
            }
            else{
            binding.tryTwo.text = ""
        }
        }
        homeViewModel.tryThree.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.tryThree.text = getString(R.string.try_three, it)
                showFinalScoreDialog()
            }else{
                binding.tryThree.text = ""
            }
        }
        homeViewModel.start.observe(viewLifecycleOwner) {
            if(it == null){
                println("start1")
                binding.clickToStart.setVisibility(View.VISIBLE)
            }else{
                println("start2")
                binding.clickToStart.setVisibility(View.GONE)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun exitGame() {
        activity?.finish()
    }
    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    private fun showFinalScoreDialog() {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Average Reaction Time")
                .setMessage(homeViewModel.avg.toString() + " ms")
                .setCancelable(false)
                .setNegativeButton("exit") { _, _ ->
                    exitGame()
                }
                .setPositiveButton("play again") { _, _ ->
                    homeViewModel.restart()
                }
                .show()
        }

}