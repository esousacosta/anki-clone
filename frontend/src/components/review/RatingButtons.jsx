import { REVIEW_RATINGS } from "../../constants/reviewRatings";

const RatingButtons = ({ hasViewedBack, cardReviewed, onRate }) => {
  if (!hasViewedBack || cardReviewed) return null;

  return (
    <div className="rate-buttons">
      <button className="rate-button again" onClick={() => onRate(REVIEW_RATINGS.AGAIN)}>Again</button>
      <button className="rate-button hard" onClick={() => onRate(REVIEW_RATINGS.HARD)}>Hard</button>
      <button className="rate-button good" onClick={() => onRate(REVIEW_RATINGS.GOOD)}>Good</button>
      <button className="rate-button easy" onClick={() => onRate(REVIEW_RATINGS.EASY)}>Easy</button>
    </div>
  );
}

export default RatingButtons;
