package util;

import javax.sound.midi.VoiceStatus;

import board.Conditions;

public class WinCheck extends Event{
		public boolean isWin=false;
		public Conditions winner=Conditions.empty;
		
		public void set(boolean b) {
			isWin=b;
		}
		public void setWinner(Conditions winner) {
			this.winner = winner;
		}
}
