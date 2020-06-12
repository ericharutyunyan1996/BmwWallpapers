package com.erikharutyunyan.bmwwallpapers.Fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.erikharutyunyan.bmwwallpapers.Activities.ScreenImageActivity
import com.erikharutyunyan.bmwwallpapers.Adapters.CarOneTypeAdapter
import com.erikharutyunyan.bmwwallpapers.Interfaces.OnFragmentInteraction
import com.erikharutyunyan.bmwwallpapers.Interfaces.OnItemsClickListener
import com.erikharutyunyan.bmwwallpapers.Models.CarImageModel
import com.erikharutyunyan.bmwwallpapers.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cars.*
import java.lang.Exception

class CarsFragment : Fragment() {
    private var carsModel = ""
    private var carsName = ""
    private var listener: OnFragmentInteraction? = null
    private lateinit var database: DatabaseReference
    private val carList by lazy { ArrayList<CarImageModel>() }
    private val adapter by lazy { CarOneTypeAdapter(requireContext(), carList) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carsName.let {
            toolbarTitle.text = it
        }
        database = FirebaseDatabase.getInstance().getReference(carsModel)
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                carList.clear()
                for (datasnaphot in p0.children) {
                    val car = datasnaphot.getValue(CarImageModel::class.java)
                    carList.add(car!!)
                    adapter.notifyDataSetChanged()
                }
                try {
                    progressCars?.let {
                        progressCars.visibility = View.GONE
                    }

                }
                catch (e:Exception){
                    Log.e("Exception",e.toString())
                }

            }
        })
        backButton.setOnClickListener {
            listener?.onBackPress()
        }
        recyclerCars.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerCars.adapter = adapter
        adapter.onItemsClickListener = object : OnItemsClickListener {
            override fun onItemClick(view: View, position: Int) {
                startActivity(
                    Intent(activity!!, ScreenImageActivity::class.java).putExtra(
                        "src",
                        carList[position].image
                    ).putExtra(
                        "title",
                        carsName
                    )
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteraction) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        private val CARSMODEL = "carsModel"
        private const val CARSNAME = "carsName"
        fun newInstance(modelNumber: String, modelName: String) = CarsFragment().apply {
            arguments = Bundle().apply {
                putString(CARSMODEL, modelNumber)
                putString(CARSNAME, modelName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            carsModel = it.getString(CARSMODEL)!!
            carsName = it.getString(CARSNAME)!!
        }
    }
}





