package com.example.vishal.prac;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    CheckBox cb;
    CheckBox ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb = (CheckBox) findViewById(R.id.check);
        ch = (CheckBox) findViewById(R.id.choco);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //CheckBox cb = (CheckBox) findViewById(R.id.check);
        //CheckBox ch = (CheckBox) findViewById(R.id.choco);
        boolean hasWc = cb.isChecked();
        boolean hasCh = ch.isChecked();
        int price = calculatePrice();
        EditText ed = (EditText) findViewById(R.id.name);
        String name = ed.getText().toString();
        String priceMessage = createOrderSummary(price,hasWc,hasCh,name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.place_order, name));
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    public String createOrderSummary(int price,boolean hasWc,boolean hasCh,String name)
    {
       String priceMessage = getString(R.string.order_summary_name, name );
       priceMessage += "\n" + getString(R.string.adWhCr, hasWc);
       priceMessage += "\n" + getString(R.string.adCh,hasCh);
       priceMessage += "\n" + getString(R.string.quant,quantity);
       priceMessage += "\n" + getString(R.string.total, price);
       priceMessage += "\n" + getString(R.string.thank_you);
        //displayMessage(priceMessage);
       return priceMessage;

    }

    public void increment(View view){
        if (quantity == 100)
        {
            Toast.makeText(this,"You cannot have more then 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }

          quantity = quantity + 1;
          displayQuantity(quantity);

    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less then 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }

            quantity = quantity - 1;
            displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + number);
    }

    /**
    * Calculates the price of the order.
    *
    * //@param quantity is the number of cups of coffee ordered
    */
    private int calculatePrice() {
        int pricePerCup = 5;
        int price = 0;
        int whippedCream = 1;
        int chocolate = 2;
        if (cb.isChecked() && ch.isChecked() )
        {
            price = (pricePerCup + whippedCream + chocolate) * quantity;
        }
        else if (cb.isChecked())
        {
            price = quantity * (pricePerCup + whippedCream);
        }
        else if (ch.isChecked())
        {
            price = quantity * (pricePerCup + chocolate);
        }
        return price;
    }


}
