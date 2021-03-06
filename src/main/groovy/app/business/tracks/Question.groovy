package app.business.tracks

class Question {

    int no

    int deckNo

    String trackCode

    String title


    Collection<AnswerOptions> answerOptions

    void setAnswerOptions(Collection<AnswerOptions> answerOptions) {
        answerOptions.eachWithIndex { answerOption, index ->
            answerOption.no = index
        }
        this.answerOptions = answerOptions
    }

    boolean isCorrect(SubmittedAnswer answerOptionNo) {
        answerOptions.find { it.correct }.no == answerOptionNo.optionNo
    }


}


