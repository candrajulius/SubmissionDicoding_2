package com.dev_candra.myapplication.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_candra.myapplication.R
import com.dev_candra.myapplication.adapter.UserAdapter
import com.dev_candra.myapplication.method.Code
import com.dev_candra.myapplication.method.SosialMedia
import com.dev_candra.myapplication.model.User
import com.dev_candra.myapplication.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sosial_media_layout.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var mainView : MainViewModel
    private val user1 = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        adapter = UserAdapter(this,user1)
        adapter.notifyDataSetChanged()
        initLayoutManager()
    }

    private fun initToolbar(){
        supportActionBar?.title = resources.getString(R.string.nama_developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }

    private fun initLayoutManager(){
        listUser.layoutManager = LinearLayoutManager(this)
        listUser.adapter = adapter
        mainView = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainView.getUser().observe(this, Observer {
            users ->
            if (users != null){
                loadingShow(false)
                if (users.size == 0){
                    textZero(true)
                    txtEmptyList.text = resources.getString(R.string.tidak_ditemukan)
                }else{
                    textZero(false)
                    adapter.setData(users)
                }
            }
        })
    }

    private fun loadingShow(show: Boolean){
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun textZero(show: Boolean){
        txtEmptyList.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isEmpty()) {
                        Code.showToast(this@MainActivity,resources.getString(R.string.cari_kosong))
                    } else {
                        adapter.clearData()
                        loadingShow(true)
                        textZero(false)
                        mainView.setUser(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    if (newText.isEmpty()){
                        Code.showToast(this@MainActivity,resources.getString(R.string.cari_kosong))
                    }else{
                        adapter.clearData()
                        loadingShow(true)
                        textZero(false)
                        mainView.setUser(newText)
                    }
                }

                return true
            }
        })


        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.sosialMedia -> sosialMedia()
            R.id.telepon -> teleponDeveloper()
        }

        return true
    }

    private fun sosialMedia(){
        val bottomSheet = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.sosial_media_layout,null)
        bottomSheet.setContentView(view)
        bottomSheet.show()

        view.btn_facebook.setOnClickListener {
            SosialMedia.btnFacebook(this@MainActivity,resources.getString(R.string.messageFacebook))
            bottomSheet.dismiss()
        }

        view.btn_whatsapp.setOnClickListener {
            SosialMedia.btnWhatsapp(this@MainActivity,resources.getString(R.string.message),resources.getString(R.string.messageWhatsapp))
            bottomSheet.dismiss()
        }

        view.btn_instagram.setOnClickListener {
            SosialMedia.btnInstagram(this@MainActivity,resources.getString(R.string.messageInstagram))
            bottomSheet.dismiss()
        }
    }

    private fun teleponDeveloper(){
        startActivity(
            Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:"+resources.getString(R.string.teleponDeveloper)))
        )
    }
}