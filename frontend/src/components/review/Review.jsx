/*
Review.jsx — explanation

- What this component does:
    Renders a simple functional React component that displays a centered "Review Page" heading with padding.

- How to use:
    <Route path="/review" element={<Review />} />   // or <Review /> anywhere in your JSX

- Notes & next steps:
    - Replace static content with state or props when you need dynamic review data.
    - Consider adding PropTypes or TypeScript types for props.
    - If used as a route page, wrap with layout components or fetch data inside useEffect.
*/
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import deckService from '../../services/deckService';
import './Review.css';

/**
 * Review Component
 * Displays a simple review page placeholder
 */
const Review = () => {
    const [isLoading, setIsLoading] = React.useState(false);
    const [decks, setDecks] = React.useState([]);
    const [error, setError] = React.useState(null);
    const navigate = useNavigate();

    const fetchingRef = React.useRef(false);

    useEffect(() => {
        console.log('Review mounted', new Date().toISOString());
        const controller = new AbortController();
        let mounted = true;
        if (fetchingRef.current) {
            console.log('Already fetching decks, aborting new fetch');
            return;
        }
        fetchingRef.current = true;
        setError(null);

        const fetchDecks = async () => {
            setIsLoading(true);
            setError(null);
            try {
                const data = await deckService.fetchDecks(controller.signal);
                if (!mounted) return;
                setDecks(data);
                console.log('Decks fetched:', data);
            } catch (err) {
                // Ignore aborts
                if (err.name === 'CanceledError' || err.name === 'AbortError') {
                    // This may happen when the component unmounts before fetch completes
                    // It is not an error we need to report to the user
                    console.log('Deck fetch aborted');
                } else {
                    console.error('Error fetching decks', err);
                    if (mounted) setError('Failed to fetch decks');
                }
            } finally {
                if (mounted) setIsLoading(false);
                fetchingRef.current = false;
            }
        };

        fetchDecks();

        return () => {
            mounted = false;
            controller.abort();
            fetchingRef.current = false;
        };
    }, []);

    return (
        <div className="review-page">
            <div className="review-header">
                <h1>Review</h1>
                <p className="muted">Choose a deck to start your review session</p>
            </div>

            {isLoading && (
                <div className="status">Loading decks…</div>
            )}

            {error && (
                <div className="status error">{error}</div>
            )}

            {!isLoading && !error && decks.length === 0 && (
                <div className="status">No decks available for review.</div>
            )}

            {!isLoading && !error && decks.length > 0 && (
                <div className="decks-grid">
                    {decks.map((deck) => (
                        <article key={deck.id} className="deck-card">
                            <h3 className="deck-title">{deck.name}</h3>
                            <p className="deck-desc">{deck.description || 'No description'}</p>
                            <div className="deck-meta">
                                <span>{deck.cards?.length ?? 0} cards</span>
                                <button onClick={() => navigate(`/decks/${deck.id}/review`)} className="btn primary">Start Review</button>
                            </div>
                        </article>
                    ))}
                </div>
            )}
        </div>
    );
}

export default Review;