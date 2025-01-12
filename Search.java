package Work1;	//Eclipseで動かしてます.package名は各環境に合わせてください.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Search {
	Node[] node;
	Node goal;
	Node start;

	ArrayList<Node> route = new ArrayList<>(); //GUI用

	Search(int whichNode) {
		makeStateSpace(whichNode);
	}

	private void makeStateSpace(int whichNode) {
		node = new Node[10];
		// 状態空間の生成
		node[0] = new Node("L.A.Airport", 0);
		node[1] = new Node("UCLA", 7);
		node[2] = new Node("Hoolywood", 4);
		node[3] = new Node("Anaheim", 6);
		node[4] = new Node("GrandCanyon", 1);
		node[5] = new Node("SanDiego", 2);
		node[6] = new Node("Downtown", 3);
		node[7] = new Node("Pasadena", 4);
		node[8] = new Node("DisneyLand", 2);
		node[9] = new Node("Las Vegas", 0);

		switch (whichNode) {
			case 1:
				//幅優先探索
        //幅優先探索が最小ステップ数となるようノードの関係性・コスト・ヒューリスティック値の変更
			node[2] = new Node("Hoolywood", 8);

  			node[0].addChild(node[1], 1);
	  		node[0].addChild(node[2], 3);
	  		node[1].addChild(node[3], 1);
	  		node[1].addChild(node[4], 6);
		  	node[2].addChild(node[9], 9);
			  node[3].addChild(node[5], 5);
	  		node[4].addChild(node[6], 4);
	   		node[4].addChild(node[7], 2);
	  		node[5].addChild(node[4], 1);
	  		node[6].addChild(node[8], 7);
		  	node[7].addChild(node[8], 1);
		  	node[7].addChild(node[9], 7);
			  node[8].addChild(node[9], 5);
		  	break;
			case 2:
				//深さ優先探索
        //深さ優先探索が最小ステップ数となるようノードの関係性・コスト・ヒューリスティック値の変更
   			node[1] = new Node("UCLA", 2);

	  		node[0].addChild(node[1], 1);
	  		node[0].addChild(node[2], 3);
		  	node[1].addChild(node[2], 1);
	  		node[1].addChild(node[3], 6);
	  		node[2].addChild(node[3], 4);
	  		node[2].addChild(node[4], 6);
	  		node[3].addChild(node[5], 5);
	  		node[3].addChild(node[6], 2);
	  		node[4].addChild(node[6], 2);
	  		node[5].addChild(node[7], 1);
	  		node[5].addChild(node[8], 1);
	  		node[6].addChild(node[7], 7);
	  		node[6].addChild(node[8], 2);
	  		node[7].addChild(node[9], 1);
	  		node[8].addChild(node[9], 5);
	  		break;
			case 3:
				//分枝限定法
				//0以上10未満の乱数を生成し、コストとする
				Random rand = new Random();

				node[0].addChild(node[1], rand.nextInt(10));
				node[0].addChild(node[2], rand.nextInt(10));
				node[1].addChild(node[2], rand.nextInt(10));
				node[1].addChild(node[6], rand.nextInt(10));
				node[2].addChild(node[3], rand.nextInt(10));
				node[2].addChild(node[6], rand.nextInt(10));
				node[2].addChild(node[7], rand.nextInt(10));
				node[3].addChild(node[4], rand.nextInt(10));
				node[3].addChild(node[7], rand.nextInt(10));
				node[3].addChild(node[8], rand.nextInt(10));
				node[4].addChild(node[8], rand.nextInt(10));
				node[4].addChild(node[9], rand.nextInt(10));
				node[5].addChild(node[1], rand.nextInt(10));
				node[6].addChild(node[5], rand.nextInt(10));
				node[6].addChild(node[7], rand.nextInt(10));
				node[7].addChild(node[8], rand.nextInt(10));
				node[7].addChild(node[9], rand.nextInt(10));
				node[8].addChild(node[9], rand.nextInt(10));
				break;
			case 4:
				//山登り法
				//node[5]のヒューリスティック値を２->５と変更
				node[5] = new Node("SanDiego", 5);

				node[0].addChild(node[1], 1);
				node[0].addChild(node[2], 3);
				node[1].addChild(node[2], 1);
				node[1].addChild(node[6], 6);
				node[2].addChild(node[3], 6);
				node[2].addChild(node[6], 6);
				node[2].addChild(node[7], 3);
				node[3].addChild(node[4], 5);
				node[3].addChild(node[7], 2);
				node[3].addChild(node[8], 4);
				node[4].addChild(node[8], 2);
				node[4].addChild(node[9], 1);
				node[5].addChild(node[1], 1);
				node[6].addChild(node[5], 7);
				node[6].addChild(node[7], 2);
				node[7].addChild(node[8], 1);
				node[7].addChild(node[9], 7);
				node[8].addChild(node[9], 5);
				break;
			case 5:
				//最良優先探索
		//バグバージョン
		//UCLA[1]のヒューリスティック値をHollywood[2]より下げてみるww
				//node[1] = new Node("UCLA", 1);
				//node[6] = new Node("Downtown", 1);
        //node[7]のヒューリスティック値を４->２と変更
				//node[7] = new Node("Pasadena", 2);

				node[0].addChild(node[1], 1);
				node[0].addChild(node[2], 3);
				node[1].addChild(node[2], 1);
				node[1].addChild(node[6], 6);
				node[2].addChild(node[3], 6);
				node[2].addChild(node[6], 6);
				node[2].addChild(node[7], 3);
				node[3].addChild(node[4], 5);
				node[3].addChild(node[7], 2);
				node[3].addChild(node[8], 4);
				node[4].addChild(node[8], 2);
				node[4].addChild(node[9], 1);
				node[5].addChild(node[1], 1);
				node[6].addChild(node[5], 7);
				node[6].addChild(node[7], 2);
				node[7].addChild(node[8], 1);
				node[7].addChild(node[9], 7);
				node[8].addChild(node[9], 5);
				break;
			case 6:
				//A*アルゴリズム
        /*node[1]のヒューリスティック値を７->３に変更
         *node[7]->node[8]のコストを１->７に変更
         *node[7]->node[9]のコストを７->１に変更
         */
        node[1] = new Node("UCLA", 3);

				node[0].addChild(node[1], 1);
				node[0].addChild(node[2], 3);
				node[1].addChild(node[2], 1);
				node[1].addChild(node[6], 6);
				node[2].addChild(node[3], 6);
				node[2].addChild(node[6], 6);
				node[2].addChild(node[7], 3);
				node[3].addChild(node[4], 5);
				node[3].addChild(node[7], 2);
				node[3].addChild(node[8], 4);
				node[4].addChild(node[8], 2);
				node[4].addChild(node[9], 1);
				node[5].addChild(node[1], 1);
				node[6].addChild(node[5], 7);
				node[6].addChild(node[7], 2);
				node[7].addChild(node[8], 7);
				node[7].addChild(node[9], 1);
				node[8].addChild(node[9], 5);
				break;
			default:
				//初期状態
				node[0].addChild(node[1], 1);
				node[0].addChild(node[2], 3);
				node[1].addChild(node[2], 1);
				node[1].addChild(node[6], 6);
				node[2].addChild(node[3], 6);
				node[2].addChild(node[6], 6);
				node[2].addChild(node[7], 3);
				node[3].addChild(node[4], 5);
				node[3].addChild(node[7], 2);
				node[3].addChild(node[8], 4);
				node[4].addChild(node[8], 2);
				node[4].addChild(node[9], 1);
				node[5].addChild(node[1], 1);
				node[6].addChild(node[5], 7);
				node[6].addChild(node[7], 2);
				node[7].addChild(node[8], 1);
				node[7].addChild(node[9], 7);
				node[8].addChild(node[9], 5);
		}
		start = node[0];
		goal = node[9];
	}

	public Node[] getNode() {
		return node;
	}

	/***
	 * 幅優先探索
	 */
	public void breadthFirst() {
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		ArrayList<Node> closed = new ArrayList<Node>();
		boolean success = false; //ゴールが見つかった場合にtrue(ループを抜ける)
		int step = 0;
		ArrayList<Node> parentList = new ArrayList<Node>();		//csv出力用 親ノード格納リスト
		ArrayList<Node> childrenList = new ArrayList<Node>();   //csv出力用 子ノード格納リスト

		for (;;) {
			System.out.println("STEP:" + (step++));
			System.out.println("OPEN:" + open.toString());
			System.out.println("CLOSED:" + closed.toString());
			// openは空か？
			if (open.size() == 0) {
				success = false; //探索失敗(ゴールが見つからずに探索すべきノードが無くなる)
				break;
			} else {
				// openの先頭を取り出し node とする．
				Node node = open.get(0);
				parentList.add(node);		//親ノード格納
				open.remove(0);
				// node は ゴールか？
				if (node == goal) {
					success = true;
					break;
				} else {
					// node を展開して子節点をすべて求める．
					ArrayList<Node> children = node.getChildren();
					// node を closed に入れる．
					closed.add(node);
					// 子節点 m が open にも closed にも含まれていなければ，
					for (int i = 0; i < children.size(); i++) {
						Node m = children.get(i);
						childrenList.add(m);			//子ノード格納
						if (!open.contains(m) && !closed.contains(m)) {
							// m から node へのポインタを付ける．
							m.setPointer(node);
							if (m == goal) {
								open.add(0, m); //mが目標ノードなら先頭(0番目)に挿入
							} else {
								open.add(m); //mが目標ノードでないなら一番後ろに挿入
							}
						}
					}
				}
				childrenList.add(null);		//STEPを区切るnullコマンドを格納
			}
		}
		if (success) {
			exportCsv(parentList, childrenList, "breadthFirst");   //csv出力メソッド呼び出し
			System.out.println("*** Solution ***");
			printSolution(goal);
		}
	}

	/***
	 * 深さ優先探索
	 */
	public void depthFirst() {
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		ArrayList<Node> closed = new ArrayList<Node>();
		boolean success = false;
		int step = 0;
		ArrayList<Node> parentList = new ArrayList<Node>();		//csv出力用 親ノード格納リスト
		ArrayList<Node> childrenList = new ArrayList<Node>();   //csv出力用 子ノード格納リスト

		for (;;) {
			System.out.println("STEP:" + (step++));
			System.out.println("OPEN:" + open.toString());
			System.out.println("CLOSED:" + closed.toString());
			// openは空か？
			if (open.size() == 0) {
				success = false;
				break;
			} else {
				// openの先頭を取り出し node とする．
				Node node = open.get(0);
				parentList.add(node);		//親ノード格納
				open.remove(0);
				// node は ゴールか？
				if (node == goal) {
					success = true;
					break;
				} else {
					// node を展開して子節点をすべて求める．
					ArrayList<Node> children = node.getChildren();
					// node を closed に入れる．
					closed.add(node);
					// 子節点 m が open にも closed にも含まれていなければ，
					// 以下を実行．幅優先探索と異なるのはこの部分である．
					// j は複数の子節点を適切にopenの先頭に置くために位置
					// を調整する変数であり，一般には展開したときの子節点
					// の位置は任意でかまわない．
					int j = 0;
					for (int i = 0; i < children.size(); i++) {
						Node m = children.get(i);
						childrenList.add(m);			//子ノード格納
						if (!open.contains(m) && !closed.contains(m)) {
							// m から node へのポインタを付ける
							m.setPointer(node);
							if (m == goal) {
								open.add(0, m); //これの必要性は？success/breakはダメ？
							} else {
								open.add(j, m); //子ノードは全て今までのリストの前に順に加える
							}
							j++;
						}
					}
				}
				childrenList.add(null);		//STEPを区切るnullコマンドを格納
			}
		}
		if (success) {
			exportCsv(parentList, childrenList, "depthFirst");   //csv出力メソッド呼び出し
			System.out.println("*** Solution ***");
			printSolution(goal);
		}
	}

	/***
	 * 分岐限定法
	 */
	public void branchAndBound() {
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		start.setGValue(0);
		ArrayList<Node> closed = new ArrayList<Node>();
		boolean success = false;
		int step = 0;
		ArrayList<Node> parentList = new ArrayList<Node>();		//csv出力用 親ノード格納リスト
		ArrayList<Node> childrenList = new ArrayList<Node>();   //csv出力用 子ノード格納リスト

		for (;;) {
			System.out.println("STEP:" + (step++));
			System.out.println("OPEN:" + open.toString());
			System.out.println("CLOSED:" + closed.toString());
			// openは空か？
			if (open.size() == 0) {
				success = false;
				break;
			} else {
				// openの先頭を取り出し node とする．
				Node node = open.get(0);
				parentList.add(node);		//親ノード格納
				open.remove(0);
				// node は ゴールか？
				if (node == goal) {
					success = true;
					break;
				} else {
					// node を展開して子節点をすべて求める．
					ArrayList<Node> children = node.getChildren();
					// node を closed に入れる．
					closed.add(node);
					for (int i = 0; i < children.size(); i++) {
						Node m = children.get(i);
						childrenList.add(m);			//子ノード格納
						// 子節点mがopenにもclosedにも含まれていなければ，
						if (!open.contains(m) && !closed.contains(m)) {
							// m から node へのポインタを付ける．
							m.setPointer(node);
							// nodeまでの評価値とnode->mのコストを
							// 足したものをmの評価値とする
							int gmn = node.getGValue() + node.getCost(m);
							m.setGValue(gmn);
							open.add(m);
						}
						// 子節点mがopenに含まれているならば，
						if (open.contains(m)) {
							int gmn = node.getGValue() + node.getCost(m);
							if (gmn < m.getGValue()) {
								m.setGValue(gmn);
								m.setPointer(node);
							}
						}
					}
				}
				childrenList.add(null);		//STEPを区切るnullコマンドを格納
			}
			open = sortUpperByGValue(open);
		}
		exportCsv(parentList, childrenList, "branchAndBound");   //csv出力メソッド呼び出し
		if (success) {
			System.out.println("*** Solution ***");
			printSolution(goal);
		}
	}

	/***
	 * 山登り法
	 */
	public void hillClimbing() {
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		start.setGValue(0);
		//ArrayList<Node> closed = new ArrayList<Node>();
		boolean success = false;
		ArrayList<Node> parentList = new ArrayList<Node>();		//csv出力用 親ノード格納リスト
		ArrayList<Node> childrenList = new ArrayList<Node>();   //csv出力用 子ノード格納リスト

		// Start を node とする．
		Node node = start;
		for (;;) {
			// node は ゴールか？
			parentList.add(node);		//親ノード格納
			if (node == goal) {
				success = true;
				break;
			} else {
				// node を展開して子節点をすべて求める．
				ArrayList<Node> children = node.getChildren();
				System.out.println(children.toString());
				for (int i = 0; i < children.size(); i++) {
					Node m = children.get(i);
					// m から node へのポインタを付ける．
					m.setPointer(node);
				}
				// 子節点の中に goal があれば goal を node とする．
				// なければ，最小の hValue を持つ子節点 m を node
				// とする．
				boolean goalp = false;
				Node min = children.get(0);
				for (int i = 0; i < children.size(); i++) {
					Node a = children.get(i);
					childrenList.add(a);		//子ノード格納
					if (a == goal) {
						goalp = true;
					} else if (min.getHValue() > a.getHValue()) {
						min = a;
					}
				}
				if (goalp) {
					node = goal;
				} else {
					node = min;
				}
			}
			childrenList.add(null);		//STEPを区切るnullコマンドを格納
		}
		exportCsv(parentList, childrenList, "hillClimbing");   //csv出力メソッド呼び出し
		if (success) {
			System.out.println("*** Solution ***");
			printSolution(goal);
		}
	}

	/***
	 * 最良優先探索
	 */
	public void bestFirst() {
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		start.setGValue(0);
		ArrayList<Node> closed = new ArrayList<Node>();
		boolean success = false;
		int step = 0;
		ArrayList<Node> parentList = new ArrayList<Node>();		//csv出力用 親ノード格納リスト
		ArrayList<Node> childrenList = new ArrayList<Node>();   //csv出力用 子ノード格納リスト

		for (;;) {
			System.out.println("STEP:" + (step++));
			System.out.println("OPEN:" + open.toString());
			System.out.println("CLOSED:" + closed.toString());
			// openは空か？
			if (open.size() == 0) {
				success = false;
				break;
			} else {
				// openの先頭を取り出し node とする．
				Node node = open.get(0);
				parentList.add(node);		//親ノード格納
				open.remove(0);
				// node は ゴールか？
				if (node == goal) {
					success = true;
					break;
				} else {
					// node を展開して子節点をすべて求める．
					ArrayList<Node> children = node.getChildren();
					// node を closed に入れる．
					closed.add(node);
					for (int i = 0; i < children.size(); i++) {
						Node m = children.get(i);

						childrenList.add(m);		//子ノード格納

						// 子節点mがopenにもclosedにも含まれていなければ，
						if (!open.contains(m) && !closed.contains(m)) {
							// m から node へのポインタを付ける．
							m.setPointer(node);
							open.add(m);
						}
					}
					childrenList.add(null);		//STEPを区切るnullコマンドを格納
				}
			}
			open = sortUpperByHValue(open);
		}
		exportCsv(parentList,childrenList, "bestFirst");   //csv出力メソッド呼び出し
		if (success) {
			System.out.println("*** Solution ***");
			printSolution(goal);
		}
	}

	/***
	 * A* アルゴリズム
	 */
	public void aStar() {
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		start.setGValue(0);
		start.setFValue(0);
		ArrayList<Node> closed = new ArrayList<Node>();
		boolean success = false;
		int step = 0;

		ArrayList<Node> parentList = new ArrayList<Node>();		//csv出力用 親ノード格納リスト
		ArrayList<Node> childrenList = new ArrayList<Node>();   //csv出力用 子ノード格納リスト
		//ArrayList<Integer> dataList = new ArrayList<Integer>();

		for (;;) {
			System.out.println("STEP:" + (step++));
			System.out.println("OPEN:" + open.toString());
			System.out.println("CLOSED:" + closed.toString());
			// openは空か？
			if (open.size() == 0) {
				success = false;
				break;
			} else {
				// openの先頭を取り出し node とする．
				Node node = open.get(0);
				System.out.println("parentNode = " + node);
				parentList.add(node);		//親ノード格納

				open.remove(0);
				// node は ゴールか？
				if (node == goal) {
					success = true;
					break;
				} else {
					// node を展開して子節点をすべて求める．
					ArrayList<Node> children = node.getChildren();
					// node を closed に入れる．
					closed.add(node);
					for (int i = 0; i < children.size(); i++) {
						Node m = children.get(i);
						System.out.println("childNode = " + m);
						childrenList.add(m);		//子ノード格納

						int gmn = node.getGValue() + node.getCost(m);
						System.out.println("gmn = " + gmn);
						//dataList.add(gmn);

						int fmn = gmn + m.getHValue();
						System.out.println("hmn = " + m.getHValue());
						//dataList.add(m.getHValue());

						System.out.println("fmn = " + fmn);
						//dataList.add(fmn);

						//dataList.add(-1);

						// 各子節点mの評価値とポインタを設定する
						if (!open.contains(m) && !closed.contains(m)) {
							// 子節点mがopenにもclosedにも含まれていない場合
							// m から node へのポインタを付ける．
							m.setGValue(gmn);
							m.setFValue(fmn);
							m.setPointer(node);
							// mをopenに追加
							open.add(m);
						} else if (open.contains(m)) {
							// 子節点mがopenに含まれている場合
							if (gmn < m.getGValue()) {
								// 評価値を更新し，m から node へのポインタを付け替える
								m.setGValue(gmn);
								m.setFValue(fmn);
								m.setPointer(node);
							}
						} else if (closed.contains(m)) {
							if (gmn < m.getGValue()) {
								// 子節点mがclosedに含まれていて fmn < fm となる場合
								// 評価値を更新し，mからnodeへのポインタを付け替える
								m.setGValue(gmn);
								m.setFValue(fmn);
								m.setPointer(node);
								// 子節点mをclosedからopenに移動
								closed.remove(m);
								open.add(m);
							}
						}
					}
					childrenList.add(null);		//STEPを区切るnullコマンドを格納
				}
			}
			open = sortUpperByFValue(open);
		}

		exportCsv(parentList,childrenList,"aStar");   //csv出力メソッド呼び出し
		if (success) {
			System.out.println("*** Solution ***");
			printSolution(goal);
		}
	}



	/***
	 * 解の表示
	 */
	public void printSolution(Node theNode) {

		route.add(theNode);

		if (theNode == start) {
			System.out.println(theNode.toString());
		} else {
			System.out.print(theNode.toString() + " <- ");
			printSolution(theNode.getPointer());
		}
	}

	/***
	 * ArrayList を Node の fValue の昇順（小さい順）に列べ換える．
	 */
	public ArrayList<Node> sortUpperByFValue(ArrayList<Node> theOpen) {
		ArrayList<Node> newOpen = new ArrayList<Node>();
		Node min, tmp = null;
		while (theOpen.size() > 0) {
			min = (Node) theOpen.get(0);
			for (int i = 1; i < theOpen.size(); i++) {
				tmp = (Node) theOpen.get(i);
				if (min.getFValue() > tmp.getFValue()) {
					min = tmp;
				}
			}
			newOpen.add(min);
			theOpen.remove(min);
		}
		return newOpen;
	}


	/***
	 * ArrayList を Node の gValue の昇順（小さい順）に列べ換える．
	 */
	public ArrayList<Node> sortUpperByGValue(ArrayList<Node> theOpen) {
		ArrayList<Node> newOpen = new ArrayList<Node>();
		Node min, tmp = null;
		while (theOpen.size() > 0) {
			min = (Node) theOpen.get(0);
			for (int i = 1; i < theOpen.size(); i++) {
				tmp = (Node) theOpen.get(i);
				if (min.getGValue() > tmp.getGValue()) {
					min = tmp;
				}
			}
			newOpen.add(min);
			theOpen.remove(min);
		}
		return newOpen;
	}

	/***
	 * ArrayList を Node の hValue の昇順（小さい順）に列べ換える．
	 */
	public ArrayList<Node> sortUpperByHValue(ArrayList<Node> theOpen) {
		ArrayList<Node> newOpen = new ArrayList<Node>();
		Node min, tmp = null;
		while (theOpen.size() > 0) {
			min = (Node) theOpen.get(0);
			for (int i = 1; i < theOpen.size(); i++) {
				tmp = (Node) theOpen.get(i);
				if (min.getHValue() > tmp.getHValue()) {
					min = tmp;
				}
			}
			newOpen.add(min);
			theOpen.remove(min);
		}
		return newOpen;
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("USAGE:");
			System.out.println("java Search [Number] [NodePattern]");
			System.out.println("[Number] = 1 : Bredth First Search");
			System.out.println("[Number] = 2 : Depth  First Search");
			System.out.println("[Number] = 3 : Branch and Bound Search");
			System.out.println("[Number] = 4 : Hill Climbing Search");
			System.out.println("[Number] = 5 : Best First Search");
			System.out.println("[Number] = 6 : A star Algorithm");
			System.out.println("[NodePattern] = 1 : Bredth First Search is best");
			System.out.println("[NodePattern] = 2 : Depth  First Search is best");
			System.out.println("[NodePattern] = 3 : Branch and Bound Search is best");
			System.out.println("[NodePattern] = 4 : Hill Climbing Search is best");
			System.out.println("[NodePattern] = 5 : Best First Search is best");
			System.out.println("[NodePattern] = 6 : A star Algorithm is best");
			System.out.println("[NodePattern] = other : initial value");
		} else {
			int which = Integer.parseInt(args[0]);
			int whichNode = Integer.parseInt(args[1]);
			long start = System.currentTimeMillis();
			switch (which) {
				case 1:
					// 幅優先探索
					System.out.println("\nBreadth First Search");
					(new Search(whichNode)).breadthFirst();
					break;
				case 2:
					// 深さ優先探索
					System.out.println("\nDepth First Search");
					(new Search(whichNode)).depthFirst();
					break;
				case 3:
					// 分岐限定法
					System.out.println("\nBranch and Bound Search");
					(new Search(whichNode)).branchAndBound();
					break;
				case 4:
					// 山登り法
					System.out.println("\nHill Climbing Search");
					(new Search(whichNode)).hillClimbing();
					break;
				case 5:
					// 最良優先探索
					System.out.println("\nBest First Search");
					(new Search(whichNode)).bestFirst();
					break;
				case 6:
					// A*アルゴリズム
					System.out.println("\nA star Algorithm");
					(new Search(whichNode)).aStar();
					break;
				default:
					System.out.println("Please input numbers 1 to 6");
			}
			long end = System.currentTimeMillis();
			System.out.println("探索時間: " + (end - start) + "ms");
		}
	}

	/***
	 * CSV出力メソッド
	 * 引数1:親ノード格納リスト
	 * 引数2:子ノード格納リスト
	 * 引数3:探索名格納リスト
	 */
		 public void exportCsv(List<Node> parentList, List<Node> childList, String fileName){

			 int i = 0;
			 int j = 0;
		        try {
		            // 出力ファイルの作成

		        	//String name = "aStar";

		            //FileWriter f = new FileWriter("A:\\" + fileName + ".csv", false);	//ファイルの出力場所はご自由に...
		            FileWriter f = new FileWriter("C:\\Users\\Owner\\Desktop\\" + fileName + ".csv", false);
		            PrintWriter p = new PrintWriter(new BufferedWriter(f));

		            // ヘッダーを指定する
		            p.print("ParentNode:n");
		            p.print(",");
//		            p.print("OpenList:L");
//		            p.print(",");
//		            p.print("ClosedList:C");
//		            p.print(",");
		            p.print("ChildrenNode:m");
		            p.print(",");
//		            p.print("Cost:g");
//		            p.print(",");
//		            p.print("Hyurisutics:h'");
//		            p.print(",");
//		            p.print("Value:f'");
		            p.println();

		            System.out.println("ParentList.size = " + parentList.size());
		            System.out.println("ChildList.size = " + childList.size());
		            //System.out.println("DataList.size = " + dataList.size());

		            // 内容をセットする

		            //親ノードの表示ループ
		            while(i < parentList.size()) {
		            	p.print(parentList.get(i) + ",");

		            	//子ノードの表示ループ
		            	while(j < childList.size()) {
		            		if(childList.get(j) != null) {
		            			p.print(childList.get(j) + ",");
		            			j++;
		            		}
		            		else {
		            			p.println();
		            			j++;
		            			break;
		            		}

		            	}

		            	i++;
		            }
/*
		            while(j < parentList.size()) {
		            	System.out.println("j = " + j);
		            	p.print(parentList.get(j) + ",");

		            	if(childList.get(j) != null) {
		            		p.print(childList.get(j) + ",");

		            		//(int)iを使って,dataListに順に入っているg,h,fの値を取る
		            		while(true) {
		            			System.out.println("i = " + i);
		            			if(dataList.get(i) != -1) {		//g,h,f値
		            				p.print(dataList.get(i) + ",");
		            				i++;
		            			}
		            			else {
		            				p.println();
		            				i++;
		            				break;
		            			}
		            		}
		            	}
		            	else {
		            		p.println();
		            		j++;
		            	}
		            }
*/
		            // ファイルに書き出し閉じる
		            p.close();
		            System.out.println("ファイル出力完了！");
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }

		    }


			/***
			 * GUI出力メソッド
			 */
			public ArrayList<Node> exec(int which) {

				route = new ArrayList<>();

				switch (which) {

					case 1:
						// 幅優先探索
						System.out.println("\nBreadth First Search");
						breadthFirst();
						break;

					case 2:
						// 深さ優先探索
						System.out.println("\nDepth First Search");
						depthFirst();
						break;

					case 3:
						// 分岐限定法
						System.out.println("\nBranch and Bound Search");
						branchAndBound();
						break;

					case 4:
						// 山登り法
						System.out.println("\nHill Climbing Search");
						hillClimbing();
						break;

					case 5:
						// 最良優先探索
						System.out.println("\nBest First Search");
						bestFirst();
						break;

					case 6:
						// A*アルゴリズム
						System.out.println("\nA star Algorithm");
						aStar();
						break;

					default:
						System.out.println("Please input numbers 1 to 6");
						return null;

				}
				return route;
			}
}



class Node {
	String name; //名前
	ArrayList<Node> children; //子ノード
	HashMap<Node,Integer> childrenCosts; //各子ノードまでのコストを保存
	Node pointer; // 解表示のためのポインタ
	int gValue; // コスト
	int hValue; // ヒューリスティック値
	int fValue; // 評価値
	boolean hasGValue = false;
	boolean hasFValue = false;

	Node(String theName, int theHValue) {
		name = theName;
		children = new ArrayList<Node>();
		childrenCosts = new HashMap<Node,Integer>();
		hValue = theHValue;
	}

	public String getName() {
		return name;
	}

	public void setPointer(Node theNode) {
		this.pointer = theNode;
	}

	public Node getPointer() {
		return this.pointer;
	}

	public int getGValue() {
		return gValue;
	}

	public void setGValue(int theGValue) {
		hasGValue = true;
		this.gValue = theGValue;
	}

	public int getHValue() {
		return hValue;
	}

	public void setHValue(int theHValue) {
		this.hValue = theHValue;
	}

	public int getFValue() {
		return fValue;
	}

	public void setFValue(int theFValue) {
		hasFValue = true;
		this.fValue = theFValue;
	}


	/***
	 * theChild この節点の子節点 theCost その子節点までのコスト
	 */
	public void addChild(Node theChild, int theCost) {
		children.add(theChild);
		//childrenCosts.put(theChild, new Integer(theCost));
		childrenCosts.put(theChild, Integer.valueOf(theCost));
	}


	public void remakeChild(Node theChild, int theCost) {
		if(children.contains(theChild)) {
			childrenCosts.replace(theChild, Integer.valueOf(theCost));
		} else {
			System.out.println("have no such a child");
		}
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public int getCost(Node theChild) {
		return childrenCosts.get(theChild).intValue();
	}

	public String toString() {
		String result = name + "(h:" + hValue + ")";
		if (hasGValue) {
			result = result + "(g:" + gValue + ")";
		}
		if (hasFValue) {
			result = result + "(f:" + fValue + ")";
		}
		return result;
	}

	/*
	 *GUI出力リセットメソッド
	 */
	void reset() {

		pointer = null;
		gValue = 0;
		hValue = 0;
		fValue = 0;
		hasGValue = false;
		hasFValue = false;

	}
}