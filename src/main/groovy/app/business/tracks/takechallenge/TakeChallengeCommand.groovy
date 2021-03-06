package app.business.tracks.takechallenge

import app.business.tracks.TrackRepository
import app.infrastructure.Command
import app.infrastructure.CommandHandler
import groovy.transform.Immutable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static rx.Observable.just

@Immutable
class TakeChallengeCommand implements Command<Map> {

    String trackCode

    int deckNo

    @Component
    static class Handler implements CommandHandler<Map, TakeChallengeCommand> {

        @Autowired
        TrackRepository trackRepository

        @Autowired
        OngoingChallengeRepository challengeRepository

        @Override
        rx.Observable<Map> handle(TakeChallengeCommand command) {
            def track = trackRepository.findByCode(command.trackCode)
            def deck = track.decks[command.deckNo]
            def question = deck.questions.first()

            def ongoingChallengeId = challengeRepository.newChallenge(deck)

            just([
                    "track"                    : command.trackCode,
                    "track.decksUntilNextLevel": track.decksUntilNextLevel(command.deckNo),
                    "deck"                     : command.deckNo,
                    "deck.title"               : deck.title,
                    "deck.level"               : deck.level,
                    "deck.size"                : deck.size(),
                    "question"                 : 0,
                    "question.title"           : question.title,
                    "question.answerOptions"   : question.answerOptions.collect { ["text": it.text] },
                    "challenge.id"             : ongoingChallengeId
            ])
        }
    }

}
