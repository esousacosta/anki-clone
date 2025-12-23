import React, { useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import deckService from '../../services/deckService';
import cardService from '../../services/cardService';
import { REVIEW_RATINGS } from '../../constants/reviewRatings';

import './DeckReviewPage.css';

const DeckReviewPage = () => {
    const { deckId } = useParams();
    const [isLoading, setIsLoading] = React.useState(false);
    const [error, setError] = React.useState(null);
    const [cards, setCards] = React.useState([]);
    const [index, setIndex] = React.useState(0);
    const [backVisible, setBackVisible] = React.useState(false);
    const [hasViewedBack, setHasViewedBack] = React.useState(false);
    const [cardReviewed, setCardReviewed] = React.useState(false);
    const [isFlipping, setIsFlipping] = React.useState(false);
    const navigate = useNavigate();

    //  This effect loads the deck’s cards when the component mounts or when deckId changes and protects against updates after unmount. Step-by-step:

    // - Hook and deps
    //     - useEffect(..., [deckId]) runs on mount and whenever deckId changes.

    // - AbortController + mounted flag
    //     - const controller = new AbortController() creates a signal you pass to the fetch so you can cancel the request when the component unmounts.
    //     - let mounted = true is an extra guard used to prevent state updates after unmount. The cleanup sets mounted = false and controller.abort().

    // - Async loader
    //     - loadCardsForReview is an async inner function that:
    //         - setIsLoading(true) and clears previous error.
    //         - calls deckService.fetchDeckById(deckId, controller.signal) to fetch the deck, passing the abort signal so the request can be cancelled.
    //         - if (!mounted) return prevents processing results if the component already unmounted between await points.
    //         - setCards(data.cards ?? []) stores the cards; the ?? [] ensures cards is an array even if data.cards is null/undefined.

    // - Error handling
    //     - The catch block ignores cancellation errors (err.name === 'CanceledError' || err.name === 'AbortError') so canceled requests don’t show errors.
    //     - Other errors are logged and setError('Failed to fetch cards for review') (only if mounted).

    // - Finally block
    //     - finally sets isLoading back to false (only if mounted) so the loading indicator is cleared regardless of success or error.

    // - Cleanup
    //     - The returned cleanup function runs on unmount (or before re-running due to deps change): it marks mounted = false and aborts the ongoing fetch via controller.abort().

    // Notes / potential improvement
    // - Right now the code uses useEffect but your file shows React.useState usage — you must either import useEffect from React (import React, { useEffect } from 'react') or use React.useEffect to avoid the "useEffect is not defined" error.

    useEffect(() => {
        const controller = new AbortController();
        let mounted = true;

        /**
         * Loads cards for review for the current deck and updates component state.
         *
         * Behavior:
         * - Sets the loading state (isLoading) to true and clears any previous error.
         * - Calls deckService.fetchDeckById(deckId, controller.signal) to fetch deck data.
         * - If the component is still mounted, updates the cards state with the fetched data.
         * - Silently ignores cancellation errors (AbortError or CanceledError).
         * - Logs unexpected errors and, if mounted, sets a user-facing error message.
         * - Ensures the loading state is cleared in a finally block (if still mounted).
         *
         * Note on async functions:
         * An "async" function is declared with the async keyword and always returns a Promise.
         * - You can use await inside an async function to pause execution until a Promise resolves or rejects.
         * - If the function returns a value, the returned Promise resolves with that value; if the function throws, the returned Promise rejects with the thrown error.
         * - Async functions do not inherently support cancellation. Cancellation must be implemented by other means (for example, passing an AbortSignal like controller.signal to the underlying request).
         *    When a cancellation occurs, the underlying operation typically throws an AbortError (or similar), which you should handle explicitly (as this function does) to avoid treating cancellation as an application error.
         *
         * Important implementation notes:
         * - This function relies on external variables from its closure: deckId, controller, mounted, and state updater functions (setIsLoading, setError, setCards).
         * - It distinguishes between cancellation-related errors and other failures, only reporting/logging the latter.
         *
         * @async
         * @function loadCardsForReview
         * @returns {Promise<void>} Resolves when the fetch and state updates complete; rejects for non-abort errors thrown by the fetch call.
         */
        const loadCardsForReview = async () => {
            setIsLoading(true);
            setError(null);
            try {
                const data = await deckService.fetchDeckCardsToReviewById(deckId, controller.signal);
                if (!mounted) return;
                console.log('Cards for review fetched:', data);
                setCards(data ?? []);
            } catch (err) {
                if (err.name === 'CanceledError' || err.name === 'AbortError') return;
                console.error('Error fetching cards for review for this deck', err);
                if (mounted) setError('Failed to fetch cards for review');
            } finally {
                if (mounted) setIsLoading(false);
            }
        }

        loadCardsForReview();

        return () => {
            mounted = false;
            controller.abort();
        };
    }, [deckId]);

    return (
        <div style={{ padding: '40px', textAlign: 'center' }}>
            <h1>Deck Review Page</h1>
            {isLoading && <p>Loading cards for review...</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {!isLoading && !error && cards.length === 0 && <p>No cards available for review in this deck.</p>}
            {!isLoading && !error && cards.length > 0 && (
                <div className="card-review">
                    <div className="card-flip-container">
                        <div className={`card-flip-content ${backVisible ? 'flipped' : ''}`}>
                            <div className="card-flip-front">
                                <p className="card-content"><strong>Front:</strong> {cards[index].front}</p>
                            </div>
                            <div className="card-flip-back">
                                <p className="card-content"><strong>Back:</strong> {cards[index].back}</p>
                            </div>
                        </div>
                    </div>
                    <button className="toggle-button"
                        onClick={() => {
                            setBackVisible(!backVisible);
                            if (!hasViewedBack && !backVisible) {
                                setHasViewedBack(true);
                            }
                        }}
                    >
                        {backVisible ? "Show Front" : "Show Back"}
                    </button>
                    <button className="next-button"
                        onClick={() => {
                            if (index + 1 < cards.length) {
                                setIndex(index + 1);
                                setBackVisible(false);
                                setHasViewedBack(false);
                                setCardReviewed(false);
                            } else {
                                alert('Review session completed!');
                                navigate('/review');
                            }
                        }}
                    >
                        Next
                    </button>
                    {hasViewedBack && !cardReviewed && <div className="rate-buttons">
                        <button className="rate-button again"
                            onClick={() => {
                                cardService.reviewCard(cards[index].id, REVIEW_RATINGS.AGAIN)
                                setCardReviewed(true);
                            }}>
                            Again</button>
                        <button className="rate-button hard"
                            onClick={() => {
                                cardService.reviewCard(cards[index].id, REVIEW_RATINGS.HARD)
                                setCardReviewed(true);
                            }}>
                            Hard</button>
                        <button className="rate-button good"
                            onClick={() => {
                                cardService.reviewCard(cards[index].id, REVIEW_RATINGS.GOOD)
                                setCardReviewed(true);
                            }}>
                            Good</button>
                        <button className="rate-button easy"
                            onClick={() => {
                                cardService.reviewCard(cards[index].id, REVIEW_RATINGS.EASY)
                                setCardReviewed(true);
                            }}>
                            Easy</button>
                    </div>}
                </div>
            )}
        </div>
    )
}

export default DeckReviewPage;