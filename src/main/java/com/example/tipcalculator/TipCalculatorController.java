package com.example.tipcalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class TipCalculatorController {

    @FXML
    private void calculateButtonPressed(ActionEvent event) {
        calculatingTip();
    }

    // formatters for currency and percentages
    private static final NumberFormat currency =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent =
            NumberFormat.getPercentInstance();

    private BigDecimal tipPercentage = new BigDecimal(0.15); // 15% default

    // GUI controls defined in FXML and used by the controller's code
    @FXML
    private TextField amountTextField;

    @FXML
    private Label tipPercentageLabel;

    @FXML
    private Slider tipPercentageSlider;

    @FXML
    private TextField tipTextField;

    @FXML
    private TextField totalTextField;

    // calculates and displays the tip and total amounts


    // called by FXMLLoader to initialize the controller
    public void initialize() {
        // 0-4 rounds down, 5-9 rounds up
        currency.setRoundingMode(RoundingMode.HALF_UP);

        tipPercentageLabel.textProperty().bind(
                Bindings.createStringBinding(() ->
                        percent.format(tipPercentageSlider.getValue()/100.00),
                        tipPercentageSlider.valueProperty()
        )
        );

        tipPercentageSlider.valueProperty().addListener((a,b,c) -> {
            tipPercentage = BigDecimal.valueOf(c.doubleValue()/100.0);
            calculatingTip();
        }
    );
        amountTextField.textProperty().addListener((a,b,c) -> calculatingTip());


    }

    private void calculatingTip() {
        try {
            BigDecimal amount = new BigDecimal(amountTextField.getText());
            BigDecimal tip = amount.multiply(tipPercentage);
            BigDecimal totalAmount= amount.add(tip);

            tipTextField.setText(currency.format(tip));
            totalTextField.setText(currency.format(totalAmount));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}