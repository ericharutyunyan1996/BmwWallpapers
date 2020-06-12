package com.erikharutyunyan.bmwwallpapers.Fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.erikharutyunyan.bmwwallpapers.Adapters.CarTypesAdapter
import com.erikharutyunyan.bmwwallpapers.Interfaces.OnFragmentInteraction
import com.erikharutyunyan.bmwwallpapers.Interfaces.OnItemsClickListener
import com.erikharutyunyan.bmwwallpapers.Models.CarTypes
import com.erikharutyunyan.bmwwallpapers.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val carList by lazy { ArrayList<CarTypes>() }
    private val adapter by lazy { CarTypesAdapter(requireContext(), carList) }
    private var listener: OnFragmentInteraction? = null
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("CarTypes")
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context!!, "error:  " + p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                carList.clear()
                for (datasnaphot in p0.children) {
                    val car = datasnaphot.getValue(CarTypes::class.java)
                    carList.add(car!!)
                    adapter.notifyDataSetChanged()
                }
                progressMain.visibility = View.GONE
            }
        })
        recyclerCarModels.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerCarModels.adapter = adapter
        adapter.carTypeClick = object : OnItemsClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.i("positionOfCar", position.toString())
                listener!!.pushFragment(
                    CarsFragment.newInstance(
                        carList[position].modelSent!!,
                        carList[position].modelName!!
                    )
                )
            }
        }
    }
}