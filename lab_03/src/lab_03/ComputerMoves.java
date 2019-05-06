package lab_03;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Random;

import javax.swing.JButton;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ComputerAI {
	int level();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface ClassAI {
}

@ClassAI
public class ComputerMoves {
	
	@ComputerAI(level=1)
	public int[] strategyRandom(JButton[][] field, int size, int[] lastMove) {
		int x, y;
		boolean end = false;
		do {
			Random generator = new Random();
			x = generator.nextInt(size);
			y = generator.nextInt(size);
			//System.out.println(x + " " + y);
			if (field[x][y].getText().equals("")) {
				end = true;
			}
		} while (!end);
		int[] ret = new int[2];
		ret[0] = x;
		ret[1] = y;
		return ret;
	}

	
	@ComputerAI(level=2)
	public int[] strategyClosest(JButton[][] field, int size, int[] lastMove) {
		int[] ret = new int[2];
		int lastx = lastMove[0];
		int lasty = lastMove[1];
		int x, y;
		boolean end = false;
		do {
			Random generator = new Random();
			x = generator.nextInt(size);
			y = generator.nextInt(size);
			if (field[x][y].getText().equals("")) {
				end = true;
			}
		} while (!end);
		try {
			if (field[lastx - 1][lasty + 1].getText().equals("")) {
				x = lastx - 1;
				y = lasty + 1;
			}
		} catch (Exception e) {
		}
		try {
			if (field[lastx + 1][lasty - 1].getText().equals("")) {
				x = lastx + 1;
				y = lasty - 1;
			}
		} catch (Exception e) {
		}
		try {
			if (field[lastx - 1][lasty - 1].getText().equals("")) {
				x = lastx - 1;
				y = lasty - 1;
			}
		} catch (Exception e) {
		}
		try {
			if (field[lastx + 1][lasty + 1].getText().equals("")) {
				x = lastx + 1;
				y = lasty + 1;
			}
		} catch (Exception e) {
		}
		try {
			if (field[lastx - 1][lasty].getText().equals("")) {
				x = lastx - 1;
				y = lasty;
			}
		} catch (Exception e) {
		}
		try {
			if (field[lastx + 1][lasty].getText().equals("")) {
				x = lastx + 1;
				y = lasty;
			}
		} catch (Exception e) {
		}
		try {
			if (field[lastx][lasty - 1].getText().equals("")) {
				x = lastx;
				y = lasty - 1;
			}
		} catch (Exception e) {
		}
		try {
			if (field[lastx][lasty + 1].getText().equals("")) {
				x = lastx;
				y = lasty + 1;
			}
		} catch (Exception e) {
		}
		ret[0] = x;
		ret[1] = y;
		//System.out.println(x + " " + y);
		return ret;
	}

}
