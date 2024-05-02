package emily.jacobo.myapplication

import Modelo.Conexion
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //1 - Mandar a llamar todos los elementos
        val txtNombreProducto = findViewById<EditText>(R.id.txtNombreProducto)
        val txtPrecio = findViewById<EditText>(R.id.txtPrecio)
        val txtCantidad = findViewById<EditText>(R.id.txtCantidad)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        //2 - Programar el boton de agregar
        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                //Guardar datps
                //1 - Crear un objeto de la clase conexion
                val objConexion = Conexion().cadenaConexion()

                //2 - Crear una variable que sea igual a un PrepareStatement
                val addProducto =
                    objConexion?.prepareStatement("insert into tbProductos1 values(?, ?, ?)")!!
                addProducto.setString(1, txtNombreProducto.text.toString())
                addProducto.setInt(2, txtPrecio.text.toString().toInt())
                addProducto.setInt(3, txtCantidad.text.toString().toInt())
                addProducto.executeUpdate()
            }
        }

        //3 - Programar el boton eliminar
        btnEliminar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                //Guardar datps
                //1 - Crear un objeto de la clase conexion
                val objConexion = Conexion().cadenaConexion()
                //2 - Crear una variable que sea igual a un PrepareStatement
                val deleteProducto =
                    objConexion?.prepareStatement("delete from tbProductos1 values(?, ?, ?)")!!
                deleteProducto.setString(1, txtNombreProducto.text.toString())
                deleteProducto.setInt(2, txtPrecio.text.toString().toInt())
                deleteProducto.setInt(3, txtCantidad.text.toString().toInt())
                deleteProducto.executeUpdate()
            }
        }
    }
}