package activity5;

import java.util.Random;

/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 *         Uses advanced search for keywords 
 *</li><li>
 *         Will transform statements as well as react to keywords
 *</li></ul>
 * This version uses an array to hold the default responses.
 * @author Laurie White
 * @version April 2012
 */
public class Eleanor
{
    /**
     * Get a default greeting     
     * @return a greeting
     */    
    public String getGreeting()
    {
        return "Hello, let's talk.";
    }
    
    /**
     * Gives a response to a user statement
     * 
     * @param statement
     *            the user statement
     * @return a response based on the rules given
     */
    public String getResponse(String statement)
    {
        String response = "";
        if (statement.length() == 0)
        {
            response = "Say something, please.";
        }

        else if (findKeyword(statement, "no") >= 0)
        {
            response = "Why so negative?";
        }
        else if (findKeyword(statement, "mother") >= 0
                ||findKeyword(statement, "mom") >= 0)
        {
            response = "My mother's name is Anne Hall Roosevelt";
        }else if (findKeyword(statement, "father") >= 0
                ||findKeyword(statement, "dad") >= 0)
        {   
            response = "My  father's name is Elliot Roosevelt";
        }else if (findKeyword(statement, "siblings") >= 0
                ||findKeyword(statement, "sisters") >= 0
                ||findKeyword(statement, "sister") >= 0
                ||findKeyword(statement, "sibling") >= 0
                ||findKeyword(statement, "brother") >= 0
                ||findKeyword(statement, "brothers") >= 0)
        {   
            response = "I have brothers, Elliot and Gracie(Hall). Hall died of scalet fever. I have no sisters.";
        }else if (findKeyword(statement, "What is your name") >= 0){
            response = "My name is Anna Eleanor Roosevelt, but I go by Eleanor";
        }else if (findKeyword(statement, "known for") >= 0
                || findKeyword(statement, "famous")  >= 0){
            response = "I helped draft the Universal Declaration of Human Rights. I was the first lady of the united states from 1933 to 1945 while my husband was president, and served as first chair on the UN human rights comitee";
        }else if (findKeyword(statement, "marry") >= 0
                ||findKeyword(statement, "marriage") >= 0
                ||findKeyword(statement, "husband") >= 0){
            response = "I married my husband Franklin Roosevelt in 1905";
        }else if (findKeyword(statement, "you born") >= 0
                || findKeyword(statement, "live")>= 0
                || findKeyword(statement, "old")>=0){
            response = "I was born in New York City on October 11th 1884 ";
        }else if (findKeyword(statement, "languages") >= 0){
            response = "I speak both English and French";
        }else if (findKeyword(statement, "sports") >= 0){
            response = "I played hockey, its my favorite sport";
        }else if (findKeyword(statement, "degree") >= 0){
            response = "I have 35 honary degrees from Russell Sage College, JJohn Marshall College of Law, and Oxford University";
        }else if (findKeyword(statement, "do") >= 0){
            response = "I love being a lizard person";
        }else if (findKeyword(statement, "How") >= 0){
            response = getRandomHowResponse();
        }else if (findKeyword(statement, "Why") >= 0){
            response = getRandomWhyResponse();
        }

        // Responses which require transformations
        else if (findKeyword(statement, "I want to", 0) >= 0)
        {
            response = transformIWantToStatement(statement);
        }
        //  Part of student solution
        else if (findKeyword(statement, "I want", 0) >= 0)
        {
            response = transformIWantStatement(statement);
        }

        else
        {

            // Look for a two word (you <something> me)
            // pattern
            int psn = findKeyword(statement, "you", 0);

            if (psn >= 0
                    && findKeyword(statement, "me", psn) >= 0)
            {
                response = transformYouMeStatement(statement);
            }
            else
            {
                //  Part of student solution
                // Look for a two word (I <something> you)
                // pattern
                psn = findKeyword(statement, "i", 0);

                if (psn >= 0
                        && findKeyword(statement, "you", psn) >= 0)
                {
                    response = transformIYouStatement(statement);
                }
                else
                {
                    response = getRandomResponse();
                }
            }
        }
        return response;
    }
    
    /**
     * Take a statement with "I want to <something>." and transform it into 
     * "What would it mean to <something>?"
     * @param statement the user statement, assumed to contain "I want to"
     * @return the transformed statement
     */
    private String transformIWantToStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword (statement, "I want to", 0);
        String restOfStatement = statement.substring(psn + 9).trim();
        return "What would it mean to " + restOfStatement + "?";
    }

    
    /**
     * Take a statement with "I want <something>." and transform it into 
     * "Would you really be happy if you had <something>?"
     * @param statement the user statement, assumed to contain "I want"
     * @return the transformed statement
     */
    private String transformIWantStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        int psn = findKeyword (statement, "I want", 0);
        String restOfStatement = statement.substring(psn + 6).trim();
        return "Would you really be happy if you had " + restOfStatement + "?";
    }
    
    /**
     * Take a statement with "you <something> me" and transform it into 
     * "What makes you think that I <something> you?"
     * @param statement the user statement, assumed to contain "you" followed by "me"
     * @return the transformed statement
     */
    private String transformYouMeStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        
        int psnOfYou = findKeyword (statement, "you", 0);
        int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
        
        String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
        return "What makes you think that I " + restOfStatement + " you?";
    }
    
    /**
     * Take a statement with "I <something> you" and transform it into 
     * "Why do you <something> me?"
     * @param statement the user statement, assumed to contain "I" followed by "you"
     * @return the transformed statement
     */
    private String transformIYouStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                    .length() - 1);
        }
        
        int psnOfI = findKeyword (statement, "I", 0);
        int psnOfYou = findKeyword (statement, "you", psnOfI);
        
        String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
        return "Why do you " + restOfStatement + " me?";
    }
    

    
    
    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  
     * @param statement the string to search
     * @param goal the string to search for
     * @param startPos the character of the string to begin the search at
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal, int startPos)
    {
        String phrase = statement.trim();
        //  The only change to incorporate the startPos is in the line below
        int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
        
        //  Refinement--make sure the goal isn't part of a word 
        while (psn >= 0) 
        {
            //  Find the string of length 1 before and after the word
            String before = " ", after = " "; 
            if (psn > 0)
            {
                before = phrase.substring (psn - 1, psn).toLowerCase();
            }
            if (psn + goal.length() < phrase.length())
            {
                after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
            }
            
            //  If before and after aren't letters, we've found the word
            if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
                    && ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
            {
                return psn;
            }
            
            //  The last position didn't work, so let's find the next, if there is one.
            psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
            
        }
        
        return -1;
    }
    
    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
     * @param statement the string to search
     * @param goal the string to search for
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal)
    {
        return findKeyword (statement, goal, 0);
    }
    

    private String getRandomHowResponse(){
        Random ra = new Random();
        return randomHowResponses [ra.nextInt(randomHowResponses.length)];
    }
    
    private String getRandomWhyResponse(){
        Random ran = new Random();
        return randomWhyResponse[ran.nextInt(randomWhyResponse.length)];
    }
    
     /**
     * Pick a default response to use if nothing else fits.
     * @return a non-committal string
     */
    private String getRandomResponse ()
    {
        Random r = new Random ();
        return randomResponses [r.nextInt(randomResponses.length)];
    }
    
    private String [] randomResponses = {"A woman is like a tea bag; you never know how strong it is until it's in hot water",
        "Do one thing every day that scares you.",
        "The future belongs to those who believe in the beauty of their dreams.",
        "Many people will walk in and out of your life, but only true friends will leave footprints in your heart",
        "I flew with Amelia Earhart",
        "Damm thats quite facinating",
        "Can we do this a little bit later",
        
    };
    
    private String [] randomHowResponses = {
        "Good",
        "Not too good",
        "Pretty bad",
        "It's've been better"
        
    };
    
    private String [] randomWhyResponse = {
        "Why not",
        "Because",
        "I said so",
    };
    
}
