import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main {
	/*使用
	 * 代入・読み込み・書き込み・while文・switch文・じゃんけんアルゴリズム・シャッフル
	 * クラス・メソッド・配列・ウェイト・日付取得・throw宣言・ArrayList

	　未使用
	*  継承・インターフェース・MAP・リスト
	*/

	static final int BATTLE=5;
	static final int ROUND =5;
	static final Random RMD = new Random();
	static final Scanner SC = new Scanner(System.in);
	static final int PRIZE =300;
	static final int BONUS =100;

	public static void main(String[] args) throws Exception {
		int act;
		boolean next = true;

		String name;
		int score = 0;
		int gu    = 9;
		int cyoki = 8;
		int pa    = 8;
		int win   = 0;
		int lose  = 0;
		int bwin  = 0;
		int blose = 0;

		Enemy enemys[] = new Enemy[7];
		enemys[0] = new Enemy("八我　　　　", 0,4); //<!>文字調整処理を時間あれば入れる
		enemys[1] = new Enemy("トクイ　　　", 1,0);
		enemys[2] = new Enemy("イトウ　　　", 2,4);
		enemys[3] = new Enemy("イワオ　　　", 3,1);
		enemys[4] = new Enemy("ハサミシタ　", 4,2);
		enemys[5] = new Enemy("カミキ　　　", 5,3);
		enemys[6] = new Enemy("アンドウ　　", 6,0);

		ArrayList<String> dialogue = new ArrayList<>();
		dialogue.add("「エッ！じゃんけんすればいい・・・ってコト！？」");
		dialogue.add("「君ここは初めてだろ？私で練習するといい。」");
		dialogue.add("「・・・。」");
		dialogue.add("「固い岩のようなカードをくらえ！」");
		dialogue.add("「全てを・・・切る！」");
		dialogue.add("「包み込む男・・・」");
		dialogue.add("「グーチョキパーの順番で出すね、本当だよ。」");

		ArrayList<String> cards = new ArrayList<>();
		cards.add("バランスよく持っているようです。");
		cards.add("グーに偏っているようです。");
		cards.add("チョキに偏っているようです。");
		cards.add("パーに偏っているようです。");
		cards.add("何を持っているか情報がありません。");

		ArrayList<String> tactics = new ArrayList<>();
		tactics.add("正直何を考えているのかわからない・・・。");
		tactics.add("「グー」「チョキ」「パー」の順番で出す。");
		tactics.add("こちらのカードを把握 枚数の「多い」ものに勝つ手を出す");
		tactics.add("「グー」に絶対の自信を持つようだ。");
		tactics.add("「チョキ」で勝ちたいようだ。");
		tactics.add("「パー」！とにかく「パー」！");
		tactics.add("言っていることは全て嘘のようだ。");

		int enemyact = 0;
		final String[] HUNDS = { " ", "グー", "チョキ", "パー", };
		int result; //じゃんけん判定用

		//スコア読み込み
		FileInputStream fis = new FileInputStream("point.csv");
		InputStreamReader isr = new InputStreamReader(fis, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		String[] params = line.split(",");
		HighScore scores = new HighScore(params[0], Integer.parseInt(params[1]), params[2]);
		br.close();

		//開始画面
		while (next) {
			System.out.println("****消費型じゃんゲーム！～一攫千金を目指せ！****");
			System.out.println("****         1.ゲームを開始する             ****");
			System.out.println("****         2.ゲーム説明                   ****");
			System.out.println("****         3.記録の初期化                 ****");
			System.out.println("****                                        ****");
			//時間あれば名前の長さで表示調整
			System.out.printf("     最高記録：%s %05d万   %s%n", scores.name, scores.point, scores.day);
			System.out.println("************************************************");
			System.out.print("選択＞＞");
			act = SC.nextInt();
			switch (act) {
			case 1:
				next = false;
				break;
			case 2:
				System.out.println();
				System.out.println("～～ゲーム説明～～");
				System.out.printf("このゲームではじゃんけん%d回勝負を%d人相手にして勝ち抜いていく%n",ROUND,BATTLE);
				System.out.println("ゲームになります。");
				System.out.println("相手に勝利した場合は賞金を獲得！");
				System.out.println("じゃんけんで3回勝てば勝利となります!勝ち数に応じて");
				System.out.println("ボーナスが付きますので高額賞金を狙う方は全勝を目指してみましょう！");
				Thread.sleep(4000);
				System.out.println();
				System.out.println("～～じゃんけんの「手」について～～");
				System.out.println("このじゃんけんは消費性となり最初に");
				System.out.println("グー9枚、チョキ8枚、パー8枚が配られます。");
				System.out.println("0枚になってしまった手はもう出せませんので注意してください。");
				Thread.sleep(4000);
				System.out.println();
				System.out.println("～～対戦相手について～～");
				System.out.println("対戦相手についてはランダムに選ばれます。");
				System.out.println("対戦相手は何かしらの「手」のカードを5枚持っています。");
				System.out.println("相手によって出す手の「傾向」が違います対戦前の「情報」");
				System.out.println("を参考にしてみてください。");
				System.out.println();
				Thread.sleep(4000);
				break;
			case 3:
				System.out.println("記録を初期化します"); //おまけスコア初期化
				scores.name = "ナナシ　";
				scores.point = 0;
				scores.day = "yyyy/mm/dd";
				FileWrite(scores);
				break;
			default:
				System.out.println("1～3を選択してください");
				System.out.println();
				break;
			}
		}
		//名前入力
		while (true) {
			System.out.println("名前を入力してください(4文字)");
			name = SC.next();
			if (name.length() > 4) {
				System.out.println("名前は4文字以内にしてください。");
			} else {break;}
		}

		//本編
		System.out.printf("ようこそ%sさん  秘密のじゃんけん会場へ！%n", name);
		System.out.println("勝ちまくって稼いでいきましょう！");
		System.out.println();
		Thread.sleep(2500);
		System.out.println("さっそく今回の対戦相手を紹介しますね！");
		System.out.println();
		Thread.sleep(2000);

		//エネミーランダム入れ替え
		for (int i = 2; i < enemys.length - 1; i++) {
			int index = new java.util.Random().nextInt(enemys.length - i) + i;
			Enemy tmp = enemys[index];
			enemys[index] = enemys[i];
			enemys[i] = tmp;
		}

		//ここを繰り返す
		for (int i = 1; i <= BATTLE; i++) {
			if (i == BATTLE) {enemys[BATTLE] = enemys[0];}
			//対戦相手発表
			Show(bwin + blose, score, enemys);
			//時間あればanykey処理追加
			System.out.printf("%d回戦開始!！", i);
			System.out.printf("VS%s%n", enemys[i].Ename);
			System.out.println(dialogue.get(enemys[i].pattern));
			System.out.println();
			next = true;
			while (next) {
				System.out.println("1:対戦開始  2:相手の情報を見る");
				System.out.print("選択＞＞");
				act = SC.nextInt();
				switch (act) {
				case 1:
					next = false;
					break;
				case 2:
					Info(enemys[i].pattern,enemys[i].cardpattern,cards,tactics);
					System.out.println();
					break;
				default:
					System.out.println("1か2を選択してください");
					System.out.println();
					break;
				}
			}

			for (int j = 0; j < ROUND; j++) {
				System.out.printf("【%d回戦 %dラウンド】%n", i, j + 1);
				System.out.printf("%s 残カード：グー【%d】チョキ【%d】パー【%d】%n", name, gu, cyoki, pa);
				System.out.println();
				while (true) {
					System.out.println("どの手を出す？ 1:グー 2:チョキ 3:パー");
					System.out.print("選択＞＞");
					act = SC.nextInt();
					if (act > 3) {
						System.out.println("1～3を選択してください");
					} else if (act == 1 && gu == 0 || act == 2 && cyoki == 0 || act == 3 && pa == 0) {
						System.out.println("その「手」カードはもう無い！");
					} else if (act == 1) {
						gu--;
						break;
					} else if (act == 2) {
						cyoki--;
						break;
					} else if (act == 3) {
						pa--;
						break;
					}
				}
				//エネミーパターンによる手の選択
				enemyact = Jyan(enemys[i].pattern, j, gu, cyoki, pa);
				//じゃんけん判定
				result = (act - enemyact + 3) % 3;
				System.out.println();
				System.out.printf("あなたは%s!  相手は%s!%n",HUNDS[act],HUNDS[enemyact]);
				Thread.sleep(0500);
				if (result == 0) {
					System.out.println("あいこ！");
				} else if (result == 2) {
					System.out.println("あなたの勝ち！！");
					win++;
				} else {
					System.out.println("あなたの負け・・・");
					lose++;
				}
				System.out.println();
			}

			//ラウンド終了処理
			System.out.println();
			System.out.printf("%d回戦結果%n", i);
			System.out.printf("%d勝 %d負 %d引き分けで%n", win, lose, ROUND - win - lose);
			if (win > lose) {
				System.out.printf("あなたの勝ち！賞金%d万獲得！%n",PRIZE);
				score += PRIZE;
				bwin++;
			} else if (win < lose) {
				System.out.printf("あなたの負け  賞金%d万没収させて頂きます！%n",PRIZE);
				if (blose == 0) {
					System.out.println("え？減るなんて聞いてない？ただで儲けられる訳");
					System.out.println("ないじゃないですかーいやだなぁハハハ");
				}
				score -= PRIZE;
				blose++;
			} else {
				System.out.println("引き分けですね！賞金変動無しです");
				bwin++; //〇回戦管理用の為
			}
			//ボーナス判定
			if (win == ROUND) {
				System.out.printf("全勝ボーナスとして更に%d万追加されます！すごいですね！！%n",BONUS*2);
				score += BONUS*2;
			} else if (win == ROUND-1) {
				System.out.printf("%d勝ボーナスとして更に%d万追加されます！おめでとう！%n",ROUND-1,BONUS);
				score += BONUS;
			}
			System.out.println();
			win  = 0;
			lose = 0;
		}

		//5回戦まで終了
		Thread.sleep(1000);
		System.out.println("大  会  終  了 !");
		System.out.println("結果発表！");
		System.out.printf("%sさんの獲得賞金は", name);
		Thread.sleep(2000);
		System.out.printf("%d万!!%n", score);
		Thread.sleep(2000);
		System.out.println();

		//レコード更新処理
		if (score > scores.point) {
			scores.name = name;
			scores.point = score;
			scores.day = Dateget();
			System.out.println("new Record!");
			FileWrite(scores);
		}

		//ED
		if (score >= 2000) {
			System.out.println("あなたは真の強者！！勝つべくして勝ったお方！");
			System.out.printf("賞金%d万を手に豪遊するのであった fin", score);
		} else if (score > 1000) {
			System.out.println("強い！！次回があればもっと上を狙ってみよう！");
			System.out.printf("賞金%d万を手にウキウキで帰路についた fin", score);
		} else if (score > 0) {
			System.out.println("うーん・・・まぁ普通かな・・・次回あればがんばってみよう！");
			System.out.printf("賞金%d万を手に帰路についた fin", score);
		} else {
			System.out.println("おっと・・・マイナスの方ですね・・・");
			Thread.sleep(2000);
			System.out.println("あなたは黒服に連れられ姿を消した・・・ fin");
		}
	}

	static class HighScore {
		String name;
		int point;
		String day;
		HighScore(String name, int point, String day) {
			this.name = name;
			this.point = point;
			this.day = day;
		}
	}

	public static void FileWrite(HighScore scores) throws Exception {
		FileOutputStream fos = new FileOutputStream("point.csv");
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
		BufferedWriter bw = new BufferedWriter(osw);
		bw.append(scores.name + "," + (Integer.toString(scores.point)) + "," + scores.day);
		bw.close();
	}

	public static String Dateget() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(today);
		return date;
	}

	public static void Show(int round, int score, Enemy[] enemys) throws Exception {
		System.out.println("◆◇◆　対　戦　相　手　　◆◇◆");
		if (round == 0) {
			for (int i = 1 + round; i <= 4 - round; i++) {
				System.out.printf("◆◇◆ %d回戦  %s◆◇◆%n", i, enemys[i].Ename);
				if (round == 0) {Thread.sleep(2000);}
			}
			System.out.printf("◆◇◆ %d回戦  ???         ◆◇◆%n",BATTLE);
		} else {
			System.out.printf("◆◇◆ %d回戦  %s◆◇◆%n", round+1, enemys[round+1].Ename);
		}
		System.out.printf("獲得賞金:%d万%n", score);
		System.out.println();
	}

	public static void Info(int pattern,int cardpattern,ArrayList<String> cards,ArrayList<String> tactics) {
		//「手カード」情報
		System.out.print("「手」カード：");
		System.out.println(cards.get(cardpattern));
		//「傾向」情報
		System.out.print("出す手の傾向:");
		System.out.println(tactics.get(pattern));
	}

	public static int Jyan(int pattern, int raund, int gu, int cyoki, int pa) {
		if (pattern == 1) {
			if (raund == 0 || raund == 3) {
				return 1;
			} else if (raund == 1 || raund == 4) {
				return 2;
			} else if (raund == 2) {
				return 3;
			}
		} else if (pattern == 3) {
			return 1;
		} else if (pattern == 4) {
			return 2;
		} else if (pattern == 5) {
			return 3;
		} else if (pattern == 6) {
			if (raund == 0 || raund == 3) {
				return 3;
			} else if (raund == 1 || raund == 4) {
				return 1;
			} else if (raund == 2) {
				return 2;
			}
		} else if (pattern == 0) {
			int a = RMD.nextInt(3) + 1;
			return a;
		} else if (pattern == 2) {
			if (gu > cyoki && gu > pa) {
				return 3;
			} else if (cyoki > gu && cyoki > pa) {
				return 1;
			} else if (pa > gu && pa > cyoki) {
				return 2;
			} else if (gu > cyoki && gu == pa) {
				return 3;
			} else if (cyoki > gu && cyoki == pa) {
				return 2;
			} else if (gu > pa && gu == cyoki) {
				return 1;
			} else {
				return 1;
			}
		}
		return 1;
	}
}

//エネミークラス  ※エネミー側の「カード」管理は一旦無し
class Enemy {
	String Ename;
	//int Egu;
	//int Ecyoki;
	//int Epa;
	int pattern;
	int cardpattern;
	//Enemy(String Ename,int Egu,int Ecyoki,int Epa,int pattern){
	Enemy(String Ename, int pattern ,int cardpattern) {
		this.Ename = Ename;
		//this.Egu=Egu;
		//this.Ecyoki=Ecyoki;
		//this.Epa=Epa;
		this.pattern = pattern;
		this.cardpattern = cardpattern;
	}
}