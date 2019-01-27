/*
 * Identify.java
 *
 * This program is the memory game most commonly called Identify Images.
 * The object is to match two cards based on their images when selected.
 * When a card is not selected, it shows a background pattern.
 */

//Import all needed classes
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Identify extends JFrame implements ActionListener 
{   
    //this array holds all possible values for the cards - two of each value
      int[] tilemap = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18};
         
    //this creates an array of togglebuttons so they will remain selected while checking two cards
      JToggleButton[] cards = new JToggleButton[36];
      
    //holders for each cards values
      Object card1Object = null;
      Object card2Object = null;
      String card1Image = "";
      String card2Image = "";
      int card1Num = 0;
      int card2Num = 0;
      
    //variable declarations
      int cardsSelected = 0;
      int cardsLeft = 36;
      int numTries = 0;
   
    //Creates new form Identify
     
       public Identify() 
       {
       	//creates the game board and shuffles the cards
         initComponents();
         shuffleCards();
       }
    
       private void initComponents() 
       {
       
       //sets the X button, size, and the title of the JFrame
         setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         setSize(550,630);
         setTitle("Concentration Here");
         
       //creates two panels; one for the cards, and one for the buttons & label
         JPanel buttonPane = new JPanel();
         JPanel cardPane = new JPanel();
      
       //sets the layout for the button panel
         buttonPane.setLayout(new GridBagLayout());
         GridBagConstraints b = new GridBagConstraints();
         b.fill = GridBagConstraints.HORIZONTAL;
      
       //sets the layout for the card panel
         cardPane.setLayout(new GridLayout(6,6));
      	
       //creates a label to display the number of tries and the winning game message
         numTriesLabel = new JLabel();
         //first position on the second row
         b.gridx = 0;
         b.gridy = 1;
         buttonPane.add(numTriesLabel,b);
      
       //this allows for a fresh start or to replay the game.	
         replayButton = new JButton();
         //first position on the first row
         b.gridx = 0;
         b.gridy = 0;
         buttonPane.add(replayButton,b);
      
       //this allows one to exit the game
         exitButton = new JButton();
         //second position on the first row
         b.gridx = 1;
         b.gridy = 0;
         buttonPane.add(exitButton,b);
      
       //display the message in the label
         numTriesLabel.setText("Number of Tries: " + numTries);
      	
       //initialize the replayButton's values
         replayButton.setMnemonic('S');
         replayButton.setText("Start Again ?");
         replayButton.setToolTipText("Start a new game");
         replayButton.addActionListener(
                new java.awt.event.ActionListener() {
                   public void actionPerformed		   (java.awt.event.ActionEvent evt) 
		  {
                     replayButtonActionPerformed(evt);
                  }
               });
      
       //initialize the exitButton's values
         exitButton.setMnemonic('x');
         exitButton.setText("Exit");
         exitButton.setToolTipText("Quit the Game");
         exitButton.addActionListener(
                new java.awt.event.ActionListener() 
		{
                   public void actionPerformed		   (java.awt.event.ActionEvent evt) 
		   {
                     exitButtonActionPerformed(evt);
                   }
                });
      
      /* create the array of cards, and initialize them to the size and background
      adding an action listener so we know when one is activated, and also add it
      to the card panel */
      
         for (int i = 0; i < cards.length; i++)
         {
            cards[i] = new JToggleButton("Card: " + i);
            cards[i].addActionListener(this);
            cards[i].setSize(80,80);
            cards[i].setIcon(new ImageIcon(".\\Clipart\\background.jpg"));
            cards[i].setText("");
            cardPane.add(cards[i]);
         }
         
       //put the card panel on the top of the form, and the button panel on the bottom
         getContentPane().add(buttonPane,BorderLayout.SOUTH);
         getContentPane().add(cardPane,BorderLayout.NORTH);
      }
      
       private void replayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shuffleButtonActionPerformed
         shuffleCards();
      }
      
       private void shuffleCards() 
       {
       //variables declaration of a temporary holding array
         int[] temparray = new int[3];
      	
      	//the random swap will be done 5 times to make sure the tiles are throughly shuffled
         for (int shuffle = 0; shuffle < 5; shuffle++) 
	 {
            //this will swap a random tilemap value with the tilemap value of x
            for (int x = 0; x < tilemap.length; x++) 
	    {
               temparray[0] = (int)Math.floor(Math.random()*36);
               temparray[1] = tilemap[temparray[0]];
               temparray[2] = tilemap[x];
               tilemap[x] = temparray[1];
               tilemap[temparray[0]] = temparray[2];
            }
         }
            
      	/*This will set the icon depending on the tilemap value
      	 *JToggleButton does not have a field for a value, so I had to create
      	 *a separate array of numbers.  */
         for (int i = 0; i < cards.length; i++) 
	 {
          /* Uncomment the following line of code to display the images
         for testing, this will cause the background Icons to be the same
         as the selectedIcons */
         
           //cards[i].setIcon(new ImageIcon(".\\Clipart\\image"+tilemap[i]+".jpg"));
            
            cards[i].setSelectedIcon(new ImageIcon(".\\Clipart\\image"+tilemap[i]+".jpg"));
           
           //have to set the text to determine which number of card was pressed
            cards[i].setText("" + i);
            
           //reset cards to enabled, visible, and not pressed for multiple games
            cards[i].setEnabled(true);
            cards[i].setVisible(true);
            cards[i].setSelected(false);   
         }
      
         //reset these values to defaults for multiple games
         cardsLeft = 36;
         numTries = 0;
         numTriesLabel.setText("Number of Tries: " + numTries);
      
      }
      
       public void actionPerformed(ActionEvent ae)      
       {
      	//variable declarations
         int iconMarker = 0;
         int nameMarker = 0;
         String card1String = "";
         String card2String = "";
         String nameText = "";
         
         /*this is to make sure that no more than two cards are selected
          * at a time */
         cardsSelected++;
         if (cardsSelected == 3) 
	 {
            card1Object = null;
            cards[card1Num].setSelected(false);
            card2Object = null;
            cards[card2Num].setSelected(false);
            cardsSelected = 1;
         }
      	
      	/*this holds the information for the first card, and determines the image
      	 * and the number of the card that was pressed */
         if (card1Object == null) {
            card1Object = ae.getSource();
            card1String = card1Object.toString();
            iconMarker = card1String.lastIndexOf(",selectedIcon=");
            nameMarker = card1String.lastIndexOf(",text=");
            card1Image = card1String.substring(iconMarker+14,nameMarker);
            nameText = card1String.substring(nameMarker+6,card1String.length()-1);
            card1Num = Integer.parseInt(nameText);
         } 
         /*this holds the information for the second card, and determines the image
          * and the number of the card that was pressed */
         else if (card2Object == null) {
            card2Object = ae.getSource();
            card2String = card2Object.toString();
            iconMarker = card2String.lastIndexOf(",selectedIcon=");
            nameMarker = card2String.lastIndexOf(",text=");
            card2Image = card2String.substring(iconMarker+14,nameMarker);
            nameText = card2String.substring(nameMarker+6,card2String.length()-1);
            card2Num = Integer.parseInt(nameText);
         }
      
      	/*if we have two cards selected, increment the number of tries
      	 *and check to see if the images match.  If they match, reset the variables
      	 *and subtract 2 cards from the cardsLeft variable */
         if (card1Object != null && card2Object != null) {
            numTries++;
            numTriesLabel.setText("Number of Tries: " + numTries);
            if (card1Image.equals(card2Image) && (card1Num != card2Num)) {
               cards[card1Num].setVisible(false);
               cards[card2Num].setVisible(false);
               card1Object = null;
               card2Object = null;
               cardsLeft -= 2;
               cardsSelected = 0;
            }
         }
         
      	//if the game is over, change the message and set the replayButton to default
         if (cardsLeft == 0) {
            getRootPane().setDefaultButton(replayButton);
            numTriesLabel.setText("Congratulations!  You completed it in: " + numTries);
         }
      }   
   
       private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
         System.exit(0);
      }
         
       public static void main(String args[]) {
      
         java.awt.EventQueue.invokeLater(
                new Runnable() {
                   public void run() {
                     new Identify().setVisible(true);
                  }
               });
      }
    
    // Variables declaration for GUI
      private javax.swing.JPanel cardPane;
      private javax.swing.JButton exitButton;
      private javax.swing.JButton replayButton;
      private javax.swing.JLabel numTriesLabel;
    // End of variables declaration
        
   }