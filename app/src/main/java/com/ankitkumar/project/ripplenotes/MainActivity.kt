package com.ankitkumar.project.ripplenotes

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class MainActivity : AppCompatActivity(), BottomSheet.BottomSheetListener,
    ItemAdapter.OnItemClickListener, Note_MenuModel.ChangeListener {
    private val viewModel: SharedViewModel by viewModels()
    private lateinit var lottie_not_found: LottieAnimationView;
    private lateinit var searchBar: SearchView
    private lateinit var dbHelper: NotesDatabaseHelper
    private lateinit var adapter: ItemAdapter
    private var itemList: MutableList<NoteModal> = mutableListOf()
    private lateinit var recyclerList: RecyclerView

    private var user_name: String = "user-xyz-123"
    private var user_email: String = "user-123@OpenAi.com"

    private lateinit var profileFrag: Profile


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val bundle: Bundle? = intent.extras
        bundle?.let {
            user_name = bundle.getString("user_name").toString()
            user_email = bundle.getString("user_email").toString()
        }
        profileFrag = Profile(bundle)


        Toast.makeText(this, "name : $user_name and email : $user_email", Toast.LENGTH_LONG).show()
        setContentView(R.layout.activity_main)
        //finding id's 🆔
        lottie_not_found = findViewById(R.id.not_found_lottie)
        searchBar = findViewById(R.id.search_bar)
        val Toolbar: MaterialToolbar = findViewById(R.id.materialToolbar)
        val floatingButton = findViewById<ExtendedFloatingActionButton>(R.id.floatingActionButton)
        recyclerList = findViewById(R.id.recylerNoteList)

        //Database work 📦
        dbHelper = NotesDatabaseHelper(this)
        //set toolbar as actionbar
        setSupportActionBar(Toolbar)



        recyclerList.layoutManager = LinearLayoutManager(this)

        val notes = dbHelper.getAllNotes()
        for (n in notes) {
            itemList.add(n)
        }

        //step 5 : done 😏
        adapter = ItemAdapter(itemList, this)
        recyclerList.adapter = adapter

        //searching..🔍
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                filterList(query)
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return false
            }

        })

        searchBar.setOnCloseListener {
            recyclerList.visibility = View.VISIBLE
            lottie_not_found.visibility = View.GONE
            Toast.makeText(this, "clicked on x", Toast.LENGTH_SHORT).show()
            true
        }


        //Toggling Ui 🐸


        floatingButton.setOnClickListener(View.OnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
            //step 4
            bottomSheet.setListener(this)
            bottomSheet.isCancelable = true
        })


        recyclerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) floatingButton.shrink()
                else floatingButton.extend()
                super.onScrolled(recyclerView, dx, dy)
            }
        })


        //fragment


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //Implement menu options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    //Change  themes of icons
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //modify here !!

        val themeItemMode = menu?.findItem(R.id.theme_mode)
        val uiMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        themeItemMode?.setIcon(
            if (uiMode == Configuration.UI_MODE_NIGHT_YES) R.drawable.light_mode
            else R.drawable.dark_mode
        )



        return super.onPrepareOptionsMenu(menu)
    }

    //handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //modify here !!
        when (item.itemId) {

            R.id.theme_mode -> {
                val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK


                if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                recreate()
            }

            R.id.profile -> {
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
                val FM = supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, profileFrag).addToBackStack(null).commit()

            }

            R.id.settings -> {
                startActivity(Intent(this, Settings::class.java))
            }

            R.id.share -> {

            }


        }

        return super.onOptionsItemSelected(item)
    }

    //step 3
    override fun onNoteAdded(note: NoteModal) {
        itemList.add(note)
        adapter.notifyItemInserted(itemList.size - 1)
    }


    //step 4, to get this implement interface 👍
    override fun onItemClick(note: NoteModal) {
        Toast.makeText(this, "Clicked: ${note.title}", Toast.LENGTH_SHORT).show()

        val intent: Intent = Intent(this, Note::class.java)


        val bundle = Bundle()
        bundle.putString("title", note.title)
        bundle.putString("desc", note.desc)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    //step 5

    override fun onItemLongPress(note: NoteModal, view: View) {
        //   Toast.makeText(this, "Long pressed: ${note.title}", Toast.LENGTH_SHORT).show()
        /*  dbHelper.deleteNote(note.title, note.desc, this)

          for (nots in itemList) {

              if (note.title == nots.title && note.desc == nots.desc) {


                  itemList.remove(nots)
              }
          }

        adapter.notifyDataSetChanged()

         */

        val MenuModal = Note_MenuModel(this)
        MenuModal.SetMenu(this, view, note)
    }


    fun filterList(s: String?) {

        val newFilteredList: MutableList<NoteModal> = mutableListOf()

        for (n in itemList) {
            if (n.title.lowercase().contains(s.toString().lowercase()) || n.desc.lowercase()
                    .contains(s.toString().lowercase())
            ) {
                newFilteredList.add(n)

            }
        }

        adapter.filterList(newFilteredList)

        if (newFilteredList.isEmpty()) {
            lottie_not_found.visibility = View.VISIBLE
        } else {
            lottie_not_found.visibility = View.GONE
        }

    }

    override fun refreshList(note: NoteModal) {

        val removeList = itemList.filter { it.title == note.title && it.desc == note.desc }

        itemList.removeAll(removeList)
        adapter.notifyDataSetChanged()
        //  Toast.makeText(this, "Working RefreshList...😏", Toast.LENGTH_SHORT).show()
    }


}