//package com.blabla.dontruinyourlaundry.test
//
//import androidx.lifecycle.ViewModelProvider
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.blabla.dontruinyourlaundry.R
////
//class TestFragment : Fragment() {
//
//    companion object {
//
//        fun newInstance(userId: String, userAge: Int): TestFragment {
//            val args = Bundle()
//            args.putString("userId", userId)
//            args.putInt("userAge", userAge)
//            val fragment = TestFragment()
//            fragment.arguments = args
//            return fragment
//        }
//    }
//
//    private lateinit var viewModel: TestViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_test, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
//
//}