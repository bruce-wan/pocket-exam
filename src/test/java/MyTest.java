import com.catalpa.pocket.model.QuestionData;
import com.catalpa.pocket.util.RandomUtil;
import org.junit.Test;

public class MyTest {

    @Test
    public void generator() {
        int amount = 20;
        int maxSum = 20;
        int digit = 2;
        int size = 3;
        for (int index = 0; index < amount; index++) {
            boolean isComplete = false;
            int sum = 0;
            int i = 1;

            QuestionData questionData = new QuestionData();

            StringBuilder title = new StringBuilder();
            int thisInt = 0;
            do {
                int maxInt = 9 * digit;
                int minInt = -9 * digit;
                int nextInt = RandomUtil.randomInteger(minInt, maxInt);

                if (thisInt == Math.abs(nextInt)) {
                    continue;
                }

                if(nextInt != 0) {
                    int tempSum = 0;
                    if (i == 1 && nextInt > 0) {
                        title.append(nextInt);
                        i++;
                        sum = nextInt;
                        thisInt = Math.abs(nextInt);
                    } else if (i > 1 && i < size) {
                        tempSum = sum + nextInt;
                        if (tempSum >= 0 && tempSum <= maxSum) {
                            if(nextInt >= 0) {
                                title.append('+').append(nextInt);
                            } else {
                                title.append('-').append(Math.abs(nextInt));
                            }
                            i++;
                            sum = sum + nextInt;
                            thisInt = Math.abs(nextInt);
                        }
                    } else if (i == size) {
                        tempSum = sum + nextInt;
                        if (tempSum >= 0 && tempSum <= maxSum) {
                            if(nextInt >= 0) {
                                title.append('+').append(nextInt);
                            } else {
                                title.append('-').append(Math.abs(nextInt));
                            }
                            sum = sum + nextInt;
                            thisInt = Math.abs(nextInt);
                            isComplete = true;
                        }
                    }
                }
            } while (!isComplete);

            System.out.println(title.toString() + "=" + sum);
        }
    }
}
