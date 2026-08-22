import { useState, useEffect } from 'react'
import QuizQuestion from './QuizQuestion'

function QuestionList({ questions }) {
  const [score, setScore] = useState(0)
  const [answeredCount, setAnsweredCount] = useState(0)
  const [currentIndex, setCurrentIndex] = useState(0)
  const [canAdvance, setCanAdvance] = useState(false)

  useEffect(() => {
    setScore(0)
    setAnsweredCount(0)
    setCurrentIndex(0)
    setCanAdvance(false)
  }, [questions])

  if (!questions || questions.length === 0) return <p>No questions loaded yet.</p>

  const isLastQuestion = currentIndex === questions.length - 1
  const quizComplete = currentIndex >= questions.length

  function handleAnswered(isCorrect) {
    setAnsweredCount((c) => c + 1)
    if (isCorrect) setScore((s) => s + 1)
    setCanAdvance(true)
  }

  function handleNext() {
    setCurrentIndex((i) => i + 1)
    setCanAdvance(false)
  }

  if (quizComplete) {
    return (
      <div>
        <p style={{ fontWeight: 'bold' }}>
          Quiz complete! Final score: {score} / {questions.length}
        </p>
      </div>
    )
  }

  return (
    <div>
      <p style={{ fontWeight: 'bold' }}>
        Score: {score} / {answeredCount} answered — Question {currentIndex + 1} of {questions.length}
      </p>

      <QuizQuestion
        key={currentIndex}
        q={questions[currentIndex]}
        index={currentIndex}
        onAnswered={handleAnswered}
      />

      {canAdvance && (
        <button onClick={handleNext}>
          {isLastQuestion ? 'Finish Quiz' : 'Next Question'}
        </button>
      )}
    </div>
  )
}

export default QuestionList