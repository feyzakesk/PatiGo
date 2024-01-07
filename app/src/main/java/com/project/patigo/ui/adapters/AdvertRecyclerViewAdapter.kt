import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.project.patigo.data.entity.Carer
import com.project.patigo.databinding.FragmentSingleAdvertBinding
import com.project.patigo.ui.adapters.ServiceRecyclerViewAdapter
import com.project.patigo.ui.fragments.HomeFragmentDirections
import okhttp3.internal.toImmutableList


class AdvertRecyclerViewAdapter(
    private val mContext: Context,
    private val carerList: List<Carer>,
) : RecyclerView.Adapter<AdvertRecyclerViewAdapter.AdvertViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertViewHolder {
        val view = FragmentSingleAdvertBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return AdvertViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdvertViewHolder, position: Int) {
        val carer = carerList[position]
        holder.bind(carer)
    }

    override fun getItemCount(): Int = carerList.size

    inner class AdvertViewHolder(var view: FragmentSingleAdvertBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(carer: Carer) {
            with(view) {
                view.root.setOnClickListener {
                    val direction=HomeFragmentDirections.actionHomeFragmentToDetailFragment(carer)
                    Navigation.findNavController(it).navigate(direction)
                }
                Glide.with(mContext).load(carer.carerProfilePict).into(advertPictureImageView);
                carerName.text = buildString {
                    append(carer.carerName)
                    append(" ")
                    append(carer.carerSurname)
                }
                carerProvince.text = carer.carerProvince
                val flexboxLayoutManager = FlexboxLayoutManager(mContext)
                flexboxLayoutManager.apply {
                    flexDirection = FlexDirection.ROW
                    flexWrap = FlexWrap.WRAP
                }
                val items = carer.carerServices
                val adapter = ServiceRecyclerViewAdapter(mContext, items.toImmutableList())
                serviceRecyclerView.layoutManager = flexboxLayoutManager
                serviceRecyclerView.adapter = adapter

            }
        }
    }
}