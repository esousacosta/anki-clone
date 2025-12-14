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
import deckService from '../../services/deckService';

/**
 * Review Component
 * Displays a simple review page placeholder
 */
const Review = () => {
    const [isLoading, setIsLoading] = React.useState(false);
    const [decks, setDecks] = React.useState([]);
    const [error, setError] = React.useState(null);

    useEffect(() => {
        const controller = new AbortController();
        let mounted = true;
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
                    console.log('Deck fetch aborted');
                } else {
                    console.error('Error fetching decks', err);
                    if (mounted) setError('Failed to fetch decks');
                }
            } finally {
                if (mounted) setIsLoading(false);
            }
        };

        fetchDecks();

        return () => {
            mounted = false;
            controller.abort();
        };
    }, []);

    return (
        <div style={{padding: '40px', textAlign: 'center'}}>
            <h1>Review Page</h1>
        </div>
    )
}

export default Review;