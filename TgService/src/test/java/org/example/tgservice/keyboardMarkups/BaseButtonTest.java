package org.example.tgservice.keyboardMarkups;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;

class BaseButtonTest {

    BaseButton baseMarkup = new BaseButton();

    @Test
    public void inlineKeyboardMarkupTest(){
        List<InlineKeyboardButton> buttons = baseMarkup
                .inlineKeyboardMarkup().getKeyboard().get(0);

        Assertions.assertEquals("Add_Time", buttons.get(1).getCallbackData());
        Assertions.assertEquals("Добавить время", buttons.get(1).getText());

        Assertions.assertEquals(2, buttons.size());
    }

}