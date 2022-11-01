package com.ukm.iotsamplesender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ukm.iotsamplesender.adapter.SensorAdapter
import com.ukm.iotsamplesender.databinding.FragmentFirstBinding
import com.ukm.iotsamplesender.model.PowerOnModel
import com.ukm.iotsamplesender.model.SensorValue

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var ref: DocumentReference
    private lateinit var colorSensorRef: CollectionReference
    private lateinit var ultraSonicRef: CollectionReference
    private lateinit var infraredRef: CollectionReference
    private val ultrasonicAdapter by lazy {
        SensorAdapter("UltraSonic", requireContext())
    }
    private val colorSensor by lazy {
        SensorAdapter("Color", requireContext())
    }
    private val infraredSensor by lazy {
        SensorAdapter("Infrared", requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = Firebase.firestore
        ref = firestore.collection("dispenser").document("powerOn")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initCOllectionRef() {
        colorSensorRef = firestore.collection("colorSensorHistory")
        ultraSonicRef = firestore.collection("ultraSonicHistory")
        infraredRef = firestore.collection("infraredHistory")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCOllectionRef()
        observePowerOn()
        observeColorSensor()
        observeUltraSonicSensor()
        observeInfrared()
        binding.recyclerColor.adapter = colorSensor
        binding.recyclerUltrasonic.adapter = ultrasonicAdapter
        binding.recyclerInfrared.adapter = infraredSensor
        binding.buttonFirst.setOnClickListener {
            ref.update("startTask", true)
        }
    }

    private fun observePowerOn() {
        ref.addSnapshotListener { value, error ->
            if (error == null) {
                val data = value?.toObject(PowerOnModel::class.java)
                binding.buttonFirst.isEnabled = data == null || data.startTask == false
            }
        }
    }

    private fun observeColorSensor() {
        colorSensorRef.addSnapshotListener { value, error ->
            if (error == null) {
                val data = value?.map {
                    it.toObject(SensorValue::class.java)
                }
                colorSensor.setNewInstance(data?.toMutableList())
            }
        }
    }

    private fun observeUltraSonicSensor() {
        ultraSonicRef.addSnapshotListener { value, error ->
            if (error == null) {
                val data = value?.map {
                    it.toObject(SensorValue::class.java)
                }
                ultrasonicAdapter.setNewInstance(data?.toMutableList())
            }
        }
    }

    private fun observeInfrared() {
        infraredRef.addSnapshotListener { value, error ->
            if (error == null) {
                val data = value?.map {
                    it.toObject(SensorValue::class.java)
                }
                colorSensor.setNewInstance(data?.toMutableList())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}