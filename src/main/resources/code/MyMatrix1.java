import java.util.Arrays;
import java.util.Scanner;

/**
 * 矩阵类，实现矩阵的加法，矩阵乘法，点乘以及转置方法
 * 其中加法和点乘方法需要有两种实现方式
 * 1.传入一个MyMatrix对象进行2个矩阵的操作
 * 2.从控制台（console）读入一个矩阵数据，再进行操作
 * 所有的数据均为int型
 * 输入数据均默认为正确数据，不需要对输入数据进行校验
 *
 */
public class MyMatrix {

	private int[][] data;
	private int m; //矩阵的第一维长度
	private int n; //矩阵的第二维长度
	
	/**
	 * 构造函数，参数为2维int数组
	 * a[i][j]是矩阵中的第i+1行，第j+1列数据
	 * @param a
	 */
	
	
	
	public MyMatrix(int[][] a){
		this.data = a;
		m=a.length;
		n=a[0].length;
	}

	/**
	 * 返回2维int矩阵
	 * @return int[][]
	 */
	public int[][] getArray(){
		return data;
	}

	/**
	 * 返回矩阵的第一维长度
	 * @return int
	 */
	public int getM(){
		return m;
	}

	/**
	 * 返回矩阵的第二维长度
	 * @return
	 */
	public int getN(){
		return n;
	}
	
	/**
	 * 实现矩阵加法，返回一个新的矩阵
	 * @param B
	 * @return
	 */
	public MyMatrix plus(MyMatrix B){
		
		int[][] a = null;
		int[][] b = null;
		
		MyMatrix mm1 = new MyMatrix(a) ;
		MyMatrix mm2 = new MyMatrix(b) ;
		
		int[][] sum = new int [a.length][a[0].length];
		if(a.length == b.length && a[0].length == b[0].length){
			for(int i = 0 ; i < sum.length ; i++){
				for(int j = 0 ; j < sum[i].length ; j++){
					sum[i][j] = a[i][j] + b[i][j];
				
				}
			}
		}
		
		return new MyMatrix(sum);
			
		
	}
	
	/**
	 * 实现矩阵乘法，返回一个新的矩阵
	 * @param B
	 * @return
	 */
	public MyMatrix times(MyMatrix B){
		
		int[][] a = null;
		int[][] b = null;
		
		MyMatrix mm1 = new MyMatrix(a) ;
		MyMatrix mm2 = new MyMatrix(b) ;
		
		
		int[][] d = new int[a.length][b[0].length];
		
		if(a.length == b[0].length){
			for(int i = 0 ; i < a.length ; i++){
				for(int j = 0 ; j < b[i].length ; j++){
				int num = 0;
				for(int k = 0 ; k <a[i].length ; k++){
					num += a[i][k]*b[k][j];
				}
				d[i][i] = num;
				}
			}
		}
		
		return new MyMatrix(d);
	}
	
	/**
	 * 实现矩阵的点乘，返回一个新的矩阵
	 * @param b
	 * @return
	 */
	public MyMatrix times(int b){
		int[][]a = null;
		int[][]c = null;
		
		MyMatrix mm1 = new MyMatrix(a) ;
		MyMatrix mm2 = new MyMatrix(c) ;
		
		int[][]dc = new int [a.length][a[0].length];
		
		if(a.length == c.length && a[0].length == c[0].length){
			for(int i = 0 ; i < a.length ; i++){
				for(int j=0 ; j < a[0].length ; j++){
					dc[i][j] = a[i][j] + c[i][j];
				}
			}
		}
		
		return new MyMatrix(dc);
	}
	
	/**
	 * 实现矩阵的转置，返回一个新的矩阵
	 * @return
	 */
	public MyMatrix transpose(){
		int[][] a = null;
		
		MyMatrix mm1 = new MyMatrix(a) ;
		
		
		int[][] result = new int[a.length][];
		for(int i = 0 ; i < a.length ; i++){
			result[i] = new int[a[i].length];
		}
		for(int x[]:a){
			for(int e:x){
				System.out.print(e+" ");
			}
			System.out.println();
		}
		System.out.println();
		
		for(int i = 0 ; i < a.length ; i++){
			for(int j = 0 ; j < a[i].length ; j++){
				result[j][i] = a[i][j];
			}
		}
		for(int x[]:result){
			for(int e:x){
				System.out.print(e+" ");
			}
			System.out.println();
		}
		return new MyMatrix(result);
	}
	
	/**
	 * 从控制台读入矩阵数据，进行矩阵加法，读入数据格式如下：
	 * m n
	 * m * n 的数据方阵，以空格隔开
	 * example:
	 * 4 3
	 * 1 2 3 
	 * 1 2 3
	 * 1 2 3
	 * 1 2 3
	 * 返回一个新的矩阵
	 * @return
	 */
	public MyMatrix plusFromConsole(){
		Scanner sc = new Scanner(System.in);
		
		int m = sc.nextInt();
		int n = sc.nextInt();
		int[][] a = null;
		int[][] b = null;
		int[][] sums = new int [m][n];
		
		for(int i = 0 ; i < m ; i++){
			for(int j = 0 ; j < n ; j++){
				sums[i][j] = a[i][j] + b[i][j];
				}
			
		}
		
		
		return new MyMatrix(sums);
	}
	
	/**
	 * 输入格式同上方法相同
	 * 实现矩阵的乘法
	 * 返回一个新的矩阵
	 * @return
	 */
	public MyMatrix timesFromConsole(){
        Scanner sc = new Scanner(System.in);
		
		int m = sc.nextInt();
		int n = sc.nextInt();
		int[][] a = null;
		int[][] b = null;
		int[][] sumss = new int [m][n];
		
		if(m == n){
			
		for(int i = 0 ; i < m ; i++){
			for(int j = 0 ; j < n ; j++){
			    int num = 0;
				for(int k = 0 ; k <n ; k++){
					num += a[i][k]*b[k][j];
				}
				sumss[i][i] = num;
				}
			}
		}
		return new MyMatrix(sumss);
		

	}
	
	/**
	 * 打印出该矩阵的数据
	 * 起始一个空行，结束一个空行
	 * 矩阵中每一行数据呈一行，数据间以空格隔开
	 * example：
	 * 
	 * 1 2 3
	 * 1 2 3
	 * 1 2 3
	 * 1 2 3
	 * 
	 */
	public void print(){
		int[][] a = null;
		for(int i = 0 ; i< a.length ; i++){
			if(i== 0){
				System.out.print("\n");
			}
			for(int j = 0 ; j < a[0].length ; j++){
				if( j == a[0].length){
					System.out.print(a[i][j]+"\n");
				}else{
					System.out.print(a[i][j]+" ");
				}
				
			}
		}
	}
	
	/**
	 * 判断是否相等的方法，考生不要修改！！
	 */
	public boolean equals(Object obj){
		
		if(obj instanceof MyMatrix){
			MyMatrix matrix = (MyMatrix) obj;
			
			if(this.data.length != matrix.data.length){
				return false;
			}
			
			for(int i=0 ; i<this.data.length ; ++i){
				if(!Arrays.equals(this.data[i], matrix.data[i])){
					return false;
				}
			}
			
			return true;
			
		}else{
			return false;
		}
	}
	
}
