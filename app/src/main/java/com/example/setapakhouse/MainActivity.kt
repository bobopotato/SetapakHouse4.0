package com.example.setapakhouse


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.setapakhouse.Fragment.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var mToggle: ActionBarDrawerToggle
    val homeFragment = HomeFragment()
    val postFragment = PostFragment()
    val housekeepingFragment = HousekeepingFragment()
    val notificationFragment = NotificationFragment()
    val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check User Login
        val currentUser= FirebaseAuth.getInstance().currentUser

        if(currentUser==null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        }

        //drawer.closeDrawers()
        //Set Action Bar and Navigation Drawer
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_logo)
        mToggle = ActionBarDrawerToggle(this, drawer, R.string.close, R.string.open)
        drawer.addDrawerListener(mToggle)
        mToggle.syncState()


        //Navigation Drawer
        nav_drawer.setNavigationItemSelectedListener(this)

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //Set bottom navigation
        makeCurrentFragment(homeFragment)
        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_addpost -> makeCurrentFragment(postFragment)
                R.id.ic_housekeeping -> makeCurrentFragment(housekeepingFragment)
                R.id.ic_notification -> makeCurrentFragment(notificationFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }

        //log out
        logout.setOnClickListener {
            //Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
            // val intent = Intent(this, LoginActivity::class.java)
            //startActivity(intent)
        }
    }

    //Add toolbar icon(search & chat)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.topbar, menu)
        return true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.ic_search) {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            //Toast.makeText(this, "user = " + currentUser.uid, Toast.LENGTH_SHORT).show()
        }
        if (menuItem.itemId == R.id.ic_chat) {
            val intent = Intent(this, ChatroomActivity::class.java)
            startActivity(intent)
        }

        if(mToggle.onOptionsItemSelected(menuItem)){
            return true
        }

        return super.onOptionsItemSelected(menuItem)
    }


    override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {

        if(menuItem.itemId == R.id.aboutUs){
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
        if(menuItem.itemId == R.id.help){
            val intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
        }
        if(menuItem.itemId == R.id.termCondition){
            val intent = Intent(this, TermAndConditionActivity::class.java)
            startActivity(intent)
        }

        return false
    }


    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


}