package br.com.sombriks.softlock

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var compName: ComponentName? = null
    val RESULT_ENABLE = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foo()
    }

    private fun foo() {
        setContentView(R.layout.activity_main)
        val devicePolicyManager =
            getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
        compName = ComponentName(this, MainActivity::class.java)
        val isActive = devicePolicyManager!!.isAdminActive(compName!!)

        if (isActive) {
            devicePolicyManager.lockNow()
            Log.i("br.com.sombriks", "locking...")
            finish()
        } else {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Needed to lock screeen"
            )
            startActivityForResult(intent, RESULT_ENABLE)
        }
    }
}
