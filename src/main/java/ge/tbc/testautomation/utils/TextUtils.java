package ge.tbc.testautomation.utils;

import com.microsoft.playwright.Locator;
import ge.tbc.testautomation.constants.Constants;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class TextUtils {

    public static List<String> getNormalizedTexts(Locator locator) {
        return locator.allInnerTexts()
                .stream()
                .map(TextUtils::normalize)
                .toList();
    }

    public static String normalize(String text) {
        return text.replace('\u00A0', ' ').trim();
    }

    public static String getFirstLine(String text) {
        return normalize(text).split("\n")[0].trim();
    }

    public static void assertListMatches(List<String> actual, List<String> expected, Set<Integer> firstLineOnlyIndexes) {
        for (int i = 0; i < expected.size(); i++) {
            String actualValue = firstLineOnlyIndexes.contains(i)
                    ? getFirstLine(actual.get(i))
                    : actual.get(i);

            assertEquals(actualValue, expected.get(i), Constants.MISMATCH_MESSAGE + i);
        }
    }
}