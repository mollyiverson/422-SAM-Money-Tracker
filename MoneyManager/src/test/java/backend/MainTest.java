package backend;

import GUI.MoneyManagerGUI;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainTest {

    @Test
    void mainTest() {
        String[] args = new String[] { "arg1", "arg2" };

        try {
            Main.main(args);
            return;

        } catch (Exception e)
        {
            fail();
        }

    }
}