package com.brotherjing.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jp.co.worksap.intern.constants.Constants;

public class SwingConsole {
	public static void run(final JFrame frame,final int width, final int height){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				frame.setTitle(Constants.APP_NAME);
				frame.setSize(width,height);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
			
		});
	}
}
