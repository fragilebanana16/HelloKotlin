package com.example.jaydean.tutorial
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.jaydean.tutorial.data.Datasource
import java.util.*
import android.support.v7.widget.RecyclerView
import com.example.jaydean.tutorial.adapter.ItemAdapter

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT represents the "pick lemon" state
    private val SELECT = "select"
    // SQUEEZE represents the "squeeze lemon" state
    private val SQUEEZE = "squeeze"
    // DRINK represents the "drink lemonade" state
    private val DRINK = "drink"
    // RESTART represents the state where the lemonade has be drunk and the glass is empty
    private val RESTART = "restart"
    // Default the state to select
    private var lemonadeState = "select"
    // Default lemonSize to -1
    private var lemonSize = -1
    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null
    /**
     * This method is called when the Activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button) as Button
        rollButton.setOnClickListener { rollDice() }
        // Do a dice roll when the app starts
        rollDice()
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        lemonImage = findViewById(R.id.image_lemon_state) as ImageView
        setViewElements()
        lemonImage!!.setOnClickListener {
            // TODO: call the method that handles the state when the image is clicked
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            // TODO: replace 'false' with a call to the function that shows the squeeze count
            showSnackbar()
        }

        // ********************************************
        val textInput: EditText = findViewById(R.id.editText) as EditText
        val textViewTime: TextView = findViewById(R.id.textView) as TextView
        val btnTime: Button = findViewById(R.id.btnGetTime) as Button
        val switchIsWeekend: Switch = findViewById(R.id.switch1) as Switch

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        textViewTime.text = textViewTime.text.toString() + c.time.toString()
        btnTime.setOnClickListener {
            var inputTime = hour
            if (textInput.text.toString().trim().length > 0){
                inputTime = textInput.text.toString().toInt()
            }
            getBonusRules(inputTime, minute, switchIsWeekend.isChecked)
            if (inputTime in 8..17){
                val toast = Toast.makeText(this, "Still working...", Toast.LENGTH_SHORT)
                toast.show()
            }
            else if (inputTime > 20){
                val toast = Toast.makeText(this, "Bonus 15 yuan", Toast.LENGTH_SHORT)
                toast.show()
            }
            else {
                val toast = Toast.makeText(this, "Unknown Time:" + inputTime.toString() + ":" + minute.toString(), Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        // ============================== 列表 ==========================
        val textView: TextView = findViewById(R.id.textView2) as TextView
        textView.text = Datasource().loadAffirmations().size.toString()

        // Initialize data.
        val myDataset = Datasource().loadAffirmations()

        val recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }
    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    private fun getBonusRules(hours:Int,minutes:Int, isWeekend: Boolean):Int{
        var current = minutes / 60.0 + hours
        if (!isWeekend){
            if (current in 8.5..18.0){
                val toast = Toast.makeText(this, "Still working...", Toast.LENGTH_SHORT)
                toast.show()
                return  0
            }
            else if (current > 20.5){
                val toast = Toast.makeText(this, "Bonus 15 yuan", Toast.LENGTH_SHORT)
                toast.show()
                return  15
            }
            else if (current in 0.0..8.5){
                val toast = Toast.makeText(this, "Get Some Sleep, 40 yuan not worth it!", Toast.LENGTH_SHORT)
                toast.show()
                return  40
            }
            else {
                val toast = Toast.makeText(this, "Err:" + current.toString(), Toast.LENGTH_SHORT)
                toast.show()
                return  0
            }
        }
        else{
            // todo
            val toast = Toast.makeText(this, "4 to 8 hours you get 28, 9+ hours you get 43!", Toast.LENGTH_LONG)
            toast.show()
            return  0
        }



    }
    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
        // TODO: use a conditional statement like 'if' or 'when' to track the lemonadeState
        //  when the the image is clicked we may need to change state to the next step in the
        //  lemonade making progression (or at least make some changes to the current state in the
        //  case of squeezing the lemon). That should be done in this conditional statement

        when(lemonadeState){
            SELECT-> {
                lemonadeState = SQUEEZE
            }
            SQUEEZE-> {
                lemonadeState = DRINK
            }
            DRINK-> {
                lemonadeState = RESTART
            }
            else-> {
                lemonadeState = SELECT
            }
        }

        // TODO: When the image is clicked in the SELECT state, the state should become SQUEEZE
        //  - The lemonSize variable needs to be set using the 'pick()' method in the LemonTree class
        //  - The squeezeCount should be 0 since we haven't squeezed any lemons just yet.

        // TODO: When the image is clicked in the SQUEEZE state the squeezeCount needs to be
        //  INCREASED by 1 and lemonSize needs to be DECREASED by 1.
        //  - If the lemonSize has reached 0, it has been juiced and the state should become DRINK
        //  - Additionally, lemonSize is no longer relevant and should be set to -1

        // TODO: When the image is clicked in the DRINK state the state should become RESTART

        // TODO: When the image is clicked in the RESTART state the state should become SELECT

        // TODO: lastly, before the function terminates we need to set the view elements so that the
        //  UI can reflect the correct state
        setViewElements()
    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action) as TextView
        // TODO: set up a conditional that tracks the lemonadeState

        val lemonImg: ImageView = findViewById(R.id.image_lemon_state) as ImageView
        val drawableResource = when (lemonadeState) {
            SELECT -> R.drawable.lemon_tree
            SQUEEZE -> R.drawable.lemon_squeeze
            DRINK -> R.drawable.lemon_drink
            else -> {
                R.drawable.lemon_restart

            }
        }
        lemonImg.setImageResource(drawableResource)
        // TODO: for each state, the textAction TextView should be set to the corresponding string from
        //  the string resources file. The strings are named to match the state

        val textTipResource = when (lemonadeState) {
            SELECT -> resources.getString(R.string.lemon_select)
            SQUEEZE -> resources.getString(R.string.lemon_squeeze)
            DRINK -> resources.getString(R.string.lemon_drink)
            else -> resources.getString(R.string.lemon_empty_glass)
        }
        textAction.text = textTipResource

        // TODO: Additionally, for each state, the lemonImage should be set to the corresponding
        //  drawable from the drawable resources. The drawables have the same names as the strings
        //  but remember that they are drawables, not strings.
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Toast.makeText(
                this,
                squeezeText,
                Toast.LENGTH_SHORT
        ).show()
        return true
    }

    /**
     * Roll the dice and update the screen with the result.
     */
    private fun rollDice() {
        // Create new Dice object with 6 sides and roll it
        val dice = Dice(5)
        val diceRoll = dice.roll()
        val diceRoll2 = dice.roll()
        val diceRoll3 = dice.roll()

        // Find the ImageView in the layout
        val diceImage: ImageView = findViewById(R.id.imageView) as ImageView
        val diceImage2: ImageView = findViewById(R.id.imageView2) as ImageView
        val diceImage3: ImageView = findViewById(R.id.imageView3) as ImageView
        val checked: CheckBox = findViewById(R.id.checkBox) as CheckBox
        // Determine which drawable resource ID to use based on the dice roll
        val drawableResource = when (diceRoll) {
            1 -> R.drawable.badge_apex_pathfinder_v
            2 -> R.drawable.badge_apex_predator
            3 -> R.drawable.badge_lone_bot
            4 -> R.drawable.badge_master_of_the_war_games
            else -> R.drawable.badge_tormenter

        }

        val drawableResource2 = when (diceRoll2) {
            1 -> R.drawable.badge_apex_pathfinder_v
            2 -> R.drawable.badge_apex_predator
            3 -> R.drawable.badge_lone_bot
            4 -> R.drawable.badge_master_of_the_war_games
            else -> R.drawable.badge_tormenter

        }

        val drawableResource3 = when (diceRoll3) {
            1 -> R.drawable.badge_apex_pathfinder_v
            2 -> R.drawable.badge_apex_predator
            3 -> R.drawable.badge_lone_bot
            4 -> R.drawable.badge_master_of_the_war_games
            else -> R.drawable.badge_tormenter

        }

        if(diceRoll == diceRoll2 && diceRoll2 == diceRoll3 || checked.isChecked){
            val toast = Toast.makeText(this, "U Win!", Toast.LENGTH_SHORT)
            toast.show()
        }


        if(checked.isChecked){
            diceImage.setImageResource(drawableResource)
            diceImage2.setImageResource(drawableResource)
            diceImage3.setImageResource(drawableResource)
        }
        else{
            // Update the ImageView with the correct drawable resource ID
            diceImage.setImageResource(drawableResource)
            diceImage2.setImageResource(drawableResource2)
            diceImage3.setImageResource(drawableResource3)
        }


    }
}

/**
 * Dice with a fixed number of sides.
 */
class Dice(private val numSides: Int) {
    val random = Random()
    fun Random.nextInt(range: IntRange): Int {
        return range.start + nextInt(range.last - range.start)
    }
    /**
     * Do a random dice roll and return the result.
     */
    fun roll(): Int {
        return random.nextInt(1..numSides)
    }
}



/**
 * A Lemon tree class with a method to "pick" a lemon. The "size" of the lemon is randomized
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    val random = Random()
    fun Random.nextInt(range: IntRange): Int {
        return range.start + nextInt(range.last - range.start)
    }
    fun pick(): Int {
        return random.nextInt(2..4)
    }
}