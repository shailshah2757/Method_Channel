package com.example.flutter_native

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import org.json.JSONException
import org.json.JSONObject

class MainActivity : FlutterActivity() {

    private val CHANNEL = "com.example/native"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            if (call.method == "native") {
                val auth = FirebaseAuth.getInstance()
                val user: FirebaseUser? = auth.currentUser
                if (user != null) {
                    try {
                        var arg = JSONObject(call.arguments.toString()).getString("arg")
                        Toast.makeText(this, "This is Flutter Native $arg", Toast.LENGTH_SHORT)
                            .show()
                        result.success("User is signed in: ${user.displayName}")
                    } catch (e: JSONException) {
                        result.error("ERROR", "Invalid argument format", null)
                    }
                } else {
                    result.success("User not signed in")
                }
            } else {
                result.notImplemented()
            }
        }
    }

}
