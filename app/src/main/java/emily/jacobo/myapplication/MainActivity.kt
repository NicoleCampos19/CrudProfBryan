package emily.jacobo.myapplication

import Modelo.Conexion
import Modelo.ListaProductos
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Statement

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
        val rcvDatos = findViewById<RecyclerView>(R.id.rvcDatos)


        //1- Ponerle un layout a mi recycleview
        rcvDatos.layoutManager = LinearLayoutManager(this)

        //////////Función para mostrar datos
        fun obtenerDatos(): List<ListaProductos>{
            //1 - Creo un objeto de la clase conexión
            val objConexion = Conexion().cadenaConexion()

            val statement =objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbProductos1")!!

            val ListadoProductos = mutableListOf<ListaProductos>()

            //Recorrer todos los datos que me trajo el select

            while (resultSet.next()) {
                val nombre = resultSet.getString("nombreProducto")
                val producto = ListaProductos(nombre)
                ListadoProductos.add(producto)
            }
            return ListadoProductos
        }

        //Ejeutamos la función
        CoroutineScope(Dispatchers.IO).launch {
            val ejecutarFuncion = obtenerDatos()

            withContext(Dispatchers.Main){
                //Uno mi adaptador con el RecyclerView
                val miAdaptador = Adaptador(ejecutarFuncion)
                rcvDatos.adapter = miAdaptador
            }
        }

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
    }
}