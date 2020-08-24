package combination;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import combination.Gui;

//Uses Linear Algebra to create parametric functions that pass through the points given by the user in ideally the fastest possible path
public class JavaManipulation {
	
	//Finds the determinant of a BigDecimal matrix
	public static BigDecimal det(BigDecimal[][] matrix){
		BigDecimal dMtx = BigDecimal.valueOf(0);
		if (matrix.length > 3) {
			for (int q = 0; q < matrix.length; q++) {
				BigDecimal[][] nMatrix = new BigDecimal[matrix.length - 1][matrix.length - 1];
				int col2 = 0;
				for (int row = 1; row < matrix.length; row++) {
					col2 = 0;
					for (int col = 0; col < matrix.length; col++) {
						if (col != q) {
							nMatrix[row-1][col2] = matrix[row][col];
							col2++;
						}
					}
				}
				BigDecimal dM =  det(nMatrix);
				dMtx =  dMtx.add(dM.multiply((matrix[0][q].multiply(BigDecimal.valueOf((int) Math.pow(-1, q))))));
			}
		} else if (matrix.length == 3){
			dMtx = (matrix[0][0].multiply((matrix[1][1].multiply(matrix[2][2])).subtract(matrix[1][2].multiply(matrix[2][1])))).subtract(matrix[0][1].multiply((matrix[1][0].multiply(matrix[2][2])).subtract(matrix[1][2].multiply(matrix[2][0])))).add(matrix[0][2].multiply((matrix[1][0].multiply(matrix[2][1])).subtract(matrix[1][1].multiply(matrix[2][0]))));
		} else if (matrix.length == 2) {
			dMtx = (matrix[0][0].multiply(matrix[1][1])).subtract(matrix[0][1].multiply(matrix[1][0]));
		}
		return dMtx;
	}
	
	//Finds the adjoint of a BigDecimal matrix
	public static BigDecimal[][] adjoint(BigDecimal[][] matrix){
		BigDecimal[][] aMtx = new BigDecimal[matrix.length][matrix[0].length];
		for (int q2 = 0; q2<matrix.length; q2++) {
			for (int q = 0; q<matrix.length; q++) {
				BigDecimal[][] dMtx = new BigDecimal[matrix.length-1][matrix[0].length-1];
				int row2 = 0;
				int col2 = 0;			
				for (int row = 0; row < matrix.length; row++) {
					col2 = 0;
					for (int col = 0; col < matrix.length; col++) {
						if (col != q && row != q2) {
							dMtx[row2][col2] = matrix[row][col];
							col2++;
						}
					}
					if (row != q2) {
						row2++;
					}
				}
				aMtx[q2][q] = det(dMtx).multiply(BigDecimal.valueOf((int) Math.pow(-1, q+q2)));
			}
		}
		aMtx = transpose(aMtx);
		return aMtx;
	}
	
	//Transposes a BigDecimal matrix
	public static BigDecimal[][] transpose(BigDecimal[][] matrix){
		BigDecimal[][] tMtx = new BigDecimal[matrix.length][matrix.length];
		for (int q = 0; q < matrix.length; q++) {
			for (int q2 = 0; q2 < matrix.length; q2++) {
				tMtx[q2][q] = matrix[q][q2];
			}
		}
		return tMtx;
	}
	
	//Multiplies a BigDecimal matrix by a BigDecimal value
	public static BigDecimal[][] multiply(BigDecimal[][] matrix, BigDecimal value){
		BigDecimal[][] mMtx = new BigDecimal[matrix.length][matrix.length];
		for (int q = 0; q < matrix.length; q++) {
			for (int q2 = 0; q2 < matrix.length; q2++) {
				mMtx[q][q2] = matrix[q][q2].multiply(value);
			}
		}
		return mMtx;
	}
	
	//Finds the inverse of a BigDecimal matrix
	public static BigDecimal[][] inv(BigDecimal[][] matrix){
		BigDecimal[][] iMtx = new BigDecimal[matrix.length][matrix.length];
		BigDecimal deter = det(matrix);
		BigDecimal invDeter = BigDecimal.valueOf(1).divide(deter,20,BigDecimal.ROUND_HALF_UP);
		BigDecimal[][] j2 = adjoint(matrix);
		iMtx = multiply(j2, invDeter);
		return iMtx;
	}
	
	//Multiplies two BigDecimal matrices together
	public static BigDecimal[][] multiply(BigDecimal[][] matrix, BigDecimal[][] values){
		BigDecimal[][] res = new BigDecimal[matrix.length][1];
		for (int q = 0; q < matrix.length; q++) {
			for (int q2 = 0; q2 < matrix.length; q2++) {
				res[q][0] = BigDecimal.valueOf(0);
			}
		}
		for (int q = 0; q < matrix.length; q++) {
			for (int q2 = 0; q2 < matrix.length; q2++) {
				res[q][0] = res[q][0].add(matrix[q][q2].multiply(values[0][q2]));
			}
		}
		return res;
	}
	
	//Takes user input data for points and slopes, converts the input data into parametric functions.
	public static void main(String[] args) {
		//BigFraction test = new BigFraction(BigInteger.valueOf(8), BigInteger.valueOf(6));
		//test = test.reduce();
		//System.out.println(test.toString());
		String input;
		input = JOptionPane.showInputDialog("How many points? ");
		int i = Integer.parseInt(input);
		Semsod.numPoints = i;
		Semsod.inputs = new BigDecimal[i][2];
		input = JOptionPane.showInputDialog("How many slopes? ");
		int i2 = Integer.parseInt(input);
		BigDecimal[][] j = new BigDecimal[i+i2][i+i2];
		BigDecimal[][] h = new BigDecimal[i+i2][i+i2];
		BigDecimal[][] r = new BigDecimal[1][i+i2];
		BigDecimal[][] s = new BigDecimal[1][i+i2];
		for (int k = 0; k < i; k++) {
			input = JOptionPane.showInputDialog("u: " + k + ", x value: ");
			BigDecimal x = new BigDecimal(input);
			input = JOptionPane.showInputDialog("u: " + k + ", y value: ");
			BigDecimal y = new BigDecimal(input);
			Semsod.inputs[k][0] = x;
			Semsod.inputs[k][1] = y;
			r[0][k] = x;
			s[0][k] = y;
			for (int p = 0; p<i+i2; p++) {	
				j[k][p] = BigDecimal.valueOf((long) Math.pow(k, p));
				h[k][p] = BigDecimal.valueOf((long) Math.pow(k, p));
			}
		}
		//Asks for slopes of the function at points, with the specific point being denoted by the 'u' value, which starts at 0
		for (int k = 0; k < i2; k++) {
			j[k+i][0] = BigDecimal.valueOf(0);
			h[k+i][0] = BigDecimal.valueOf(0);
			input = JOptionPane.showInputDialog("u value # " + (k+1) + ": ");
			int u = Integer.parseInt(input);
			input = JOptionPane.showInputDialog("u: " + u + ", x' value: ");
			BigDecimal x = new BigDecimal(input);
			input = JOptionPane.showInputDialog("u: " + u + ", y' value: ");
			BigDecimal y = new BigDecimal(input);
			r[0][k+i] = x;
			s[0][k+i] = y;
			for (int p = 1; p<i+i2; p++) {
				j[k+i][p] =  BigDecimal.valueOf((long) (Math.pow(u, p-1)*p));
				h[k+i][p] =  BigDecimal.valueOf((long) (Math.pow(u, p-1)*p));
			}
		}
		BigDecimal[][] invJ = inv(j);
		BigDecimal[][] invH = inv(h);
		BigDecimal[][] pro = multiply(invJ, r);
		BigDecimal[][] proy = multiply(invH, s);
		
		//Uses the calculated function to set the values for the Semsod class X and Y equations 
		Semsod.xRes = "";
		for (int l = 0; l < i+i2; l++){
			Semsod.xRes = Semsod.xRes + pro[l][0];
		    if (l+1 < i+i2){
		    	Semsod.xRes = Semsod.xRes + (" ");
		    }
		}
		Semsod.yRes = "";
		for (int l = 0; l < i+i2; l++){
			Semsod.yRes = Semsod.yRes + proy[l][0];
			if (l+1 < i+i2){
				Semsod.yRes = Semsod.yRes + " ";
		    }
		}
		while (Semsod.xRes.indexOf("E") != -1){
			Semsod.xRes = Semsod.xRes.substring(0, Semsod.xRes.indexOf("E")) + Semsod.xRes.substring(Semsod.xRes.indexOf("E") +4);
    	}
		while (Semsod.yRes.indexOf("E") != -1){
			Semsod.yRes = Semsod.yRes.substring(0, Semsod.yRes.indexOf("E")) + Semsod.yRes.substring(Semsod.yRes.indexOf("E") +4);
    	}
	}
}
