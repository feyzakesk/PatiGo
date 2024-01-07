package com.project.patigo.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.patigo.data.entity.Carer
import com.project.patigo.data.entity.Comment
import com.project.patigo.data.firebase.FirebaseFirestoreResult
import com.project.patigo.data.repository.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private var firebaseFirestoreRepository: FirebaseFirestoreRepository) :
    ViewModel() {

    val resultCarers = MutableLiveData<FirebaseFirestoreResult>()

    init {
        getCarers()
    }

     fun getCarers(){
        CoroutineScope(Dispatchers.Main).launch{
            val result=firebaseFirestoreRepository.getCarers()
            resultCarers.value=result
        }
    }

//    private fun saveCarers() {
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                val dummyCarers = listOf(
//                    Carer(
//                        carerId = "1",
//                        carerName = "Ayşe",
//                        carerSurname = "Yılmaz",
//                        carerAge = 32,
//                        carerAvailableDay = listOf("Pazartesi", "Çarşamba", "Cuma"),
//                        carerIban = "TR00123456789",
//                        carerServices = listOf("Köpek Yürüyüşü", "Kedi Bakımı"),
//                        carerProvince = "Kadıköy",
//                        carerReviewList = listOf(
//                            Comment(
//                                "19.02.2020",
//                                "Mehmet",
//                                "Kaya",
//                                5.0,
//                                "Çok sabırlı ve anlayışlı bir bakıcıydı, evcil hayvanıma çok iyi baktı."
//                            ),
//                            Comment(
//                                "22.06.2021",
//                                "Ali",
//                                "Yılmaz",
//                                5.0,
//                                "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
//                            ),
//                            Comment(
//                                "15.07.2023",
//                                "Fatma",
//                                "Kaya",
//                                4.0,
//                                "Hayvan bakıcısı olarak harika bir iş çıkardı. Evcil hayvanım onunla çok mutlu!"
//                            )
//                        ),
//                        carerProfilePict = "https://randomuser.me/api/portraits/women/75.jpg",
//                        carerInfo = "Kadıköy'de yaşayan, veteriner hekim Ayşe. Cana yakın ve hayvanları çok seven bir kişilik.",
//                        carerPhoneNumber = "05001234567"
//                    ),
//                    Carer(
//                        carerId = "2",
//                        carerName = "Mehmet",
//                        carerSurname = "Özcan",
//                        carerAge = 28,
//                        carerAvailableDay = listOf("Salı", "Perşembe"),
//                        carerIban = "TR00987654321",
//                        carerServices = listOf(
//                            "Evcil Hayvan Oteli",
//                            "Pet Taksi",
//                            "Pet Taksi",
//                            "Pet Taksi",
//                            "Pet Taksi",
//                            "Pet Taksi",
//                            "Pet Taksi",
//                            "Pet Taksi"
//                        ),
//                        carerProvince = "Şişli",
//                        carerReviewList = listOf(
//                            Comment(
//                                "19.04.2022",
//                                "Fatma",
//                                "Kara",
//                                5.0,
//                                "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
//                            ),
//                            Comment(
//                                "08.06.2020",
//                                "Ali",
//                                "Kara",
//                                4.0,
//                                "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
//                            ),
//                            Comment(
//                                "21.02.2021",
//                                "Fatma",
//                                "Çelik",
//                                4.0,
//                                "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
//                            )
//                        ),
//                        carerProfilePict = "https://randomuser.me/api/portraits/men/1.jpg",
//                        carerInfo = "Şişli'de profesyonel pet oteli işletmecisi Mehmet. Hayvanlarla ilgilenmek onun için bir tutku.",
//                        carerPhoneNumber = "05007654321"
//                    ),
//                    Carer(
//                        carerId = "3",
//                        carerName = "Elif",
//                        carerSurname = "Demir",
//                        carerAge = 26,
//                        carerAvailableDay = listOf("Pazartesi", "Perşembe", "Cumartesi"),
//                        carerIban = "TR001122334455",
//                        carerServices = listOf("Köpek Eğitimi", "Kedi Bakımı"),
//                        carerProvince = "Beyoğlu",
//                        carerReviewList = listOf(
//                            Comment(
//                                "28.01.2023",
//                                "Zeynep",
//                                "Çelik",
//                                4.5,
//                                "Beklentilerimi aştı, evcil hayvanım için harika bir bakım sağladı."
//                            ),
//                            Comment(
//                                "05.06.2022",
//                                "Ayşe",
//                                "Kaya",
//                                4.5,
//                                "Hayvan bakıcısı olarak harika bir iş çıkardı. Evcil hayvanım onunla çok mutlu!"
//                            ),
//                            Comment(
//                                "04.06.2021",
//                                "Fatma",
//                                "Yılmaz",
//                                3.5,
//                                "Hayvan bakıcısı olarak harika bir iş çıkardı. Evcil hayvanım onunla çok mutlu!"
//                            )
//                        ),
//                        carerProfilePict = "https://randomuser.me/api/portraits/women/33.jpg",
//                        carerInfo = "Beyoğlu'nda yaşayan Elif, freelance grafik tasarımcı ve sertifikalı köpek eğitmeni. Evcil hayvanlara karşı özel bir ilgisi var.",
//                        carerPhoneNumber = "05001122334"
//                    )
//                )
//                for (carer in dummyCarers){
//                    firebaseFirestoreRepository.saveCarer(carer)
//                }
//
//            }catch (e: Exception){
//                Log.e("HATA",e.message.toString())
//            }
//
//        }
//    }

}

