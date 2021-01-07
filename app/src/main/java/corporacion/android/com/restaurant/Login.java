package corporacion.android.com.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    Button btnIniciar;
    EditText edtusuario,edtcontraseña;
    TextView txtValidacion,txtVistacontraseña;
    Boolean views = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnIniciar = findViewById(R.id.btnIniciar);
        edtusuario = findViewById(R.id.idusuario);
        edtcontraseña = findViewById(R.id.idcontraseña);
        txtValidacion = findViewById(R.id.validacion);
        txtVistacontraseña = findViewById(R.id.vistaContraseña);
        txtVistacontraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (views){
                    edtcontraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    views = false;
                }else{
                    edtcontraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    views = true;
                }
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    VerificaUsuario();//llama a ala funcion que verifica si existe el usuario
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    //TODO para IS de api new guardamos una referencia de los datos ingresado en login
    private void guardarPreferencialogin(){
        SharedPreferences preferences = getSharedPreferences("preferencialogin", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario",String.valueOf(edtusuario.getText()) );
        editor.putString("password",String.valueOf(edtcontraseña.getText()));
        editor.putBoolean("session",true);
        editor.commit();
    }

    private void guardarpreferenciausuario(String Celular,String ptoken,String pfoto,String pemial,String pname,String pid){
        SharedPreferences preferences = getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE); //asignamos un nombre a la preferencia
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("celular",Celular);
        editor.putString("token",ptoken);
        editor.putString("foto",pfoto);
        editor.putString("email",pemial);
        editor.putString("name",pname);
        editor.putString("id",pid);
        editor.commit();
    }

    public void VerificaUsuario()
            throws IOException {
        RequestBody formBody = new FormBody.Builder() //mandamos los parametros para validar el login
                .add("usuario",String.valueOf(edtusuario.getText()) )
                .add("password", String.valueOf(edtcontraseña.getText()))
                .build();
        Request request = new Request.Builder()
                .url("https://buqkly.com/api/login")  //url
                .post(formBody)         //parametros
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String myresponse = response.body().string();       //asignamos la respuesta en esa variable
                System.out.println(myresponse);                     //mostramos el resultado para visualizarlo
                try {
                    JSONObject json = new JSONObject(myresponse);
                    //asignamos en una variable json  el resultado
                    if (json.getBoolean("error")) {
                        System.out.println("error");
                        //si la respuesta da error, muestra un mensaje del resultado
                        Toast.makeText(getApplicationContext(), json.getString("success"), Toast.LENGTH_SHORT).show();
                    } else {                                //si el resultado es perfecto
                        guardarPreferencialogin();

                        JSONArray array = json.getJSONArray("success");
                        //asignamos al array el array que de respuesta success
                        for(int i = 0; i < array.length(); i++) {
                            JSONObject objectUsuario = array.getJSONObject(i);
                            guardarpreferenciausuario(
                                    objectUsuario.getString("celular"),
                                    objectUsuario.getString("token"),
                                    objectUsuario.getString("foto"),
                                    objectUsuario.getString("email"),
                                    objectUsuario.getString("name"),
                                    objectUsuario.getString("id")
                            );
                            //sqlUsuario.agregarUsuario(objectUsuario.getString("id"),objectUsuario.getString("name"), objectUsuario.getString("email"), objectUsuario.getString("token")); //agregamos a la BD el usuario logeado
                        }
                        Intent intent = new Intent(getApplicationContext(), Inicio.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) { //no hubo conexion con respecto al url solicitado
                e.printStackTrace();
                Log.i ("ERROR", e.getMessage()); //mostramos el mensaje del error de la conexion
            }
        });
    }

}