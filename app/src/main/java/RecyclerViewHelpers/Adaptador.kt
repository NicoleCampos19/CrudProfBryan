package RecyclerViewHelpers

import Modelo.ListaProductos
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import emily.jacobo.myapplication.R

class Adaptador(private var Datos: List<ListaProductos>): RecyclerView.Adapter<ViewHolderr>() {

    fun actualizarRecyclerView(nuevaLista: List<ListaProductos>){
        Datos = nuevaLista
        notifyDataSetChanged() //Notifica que hay datos nuevos

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderr {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity2_itam_card,parent,false)
        return ViewHolderr(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderr, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.nombreProducto
    }

}