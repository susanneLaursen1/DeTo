package com.example.deto;


import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
    public class LoginUnitTest {

        private static final String FAKE_STRING = "Login was successful";

        @Mock
        Context mMockContext;



        @Test
        public void readStringFromContext_LocalizedString() {

            MainActivity myObjectUnderTest = new MainActivity(mMockContext);

            // ...when the string is returned from the object under test...
            String result = myObjectUnderTest.validate("Deto", "2020");

            // ...then the result should be the expected one.
            assertThat(result, is(FAKE_STRING));
        }

}

