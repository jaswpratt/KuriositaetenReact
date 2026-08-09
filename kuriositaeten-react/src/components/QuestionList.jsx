import { useState, useEffect } from 'react'
import QuizQuestion from './QuizQuestion'

function QuestionList({ questions }) {
  const [score, setScore] = useState(0)
  const [answeredCount, setAnsweredCount] = useState(0)

  // reset score whenever a fresh set of questions loads
  useEffect(() => {
    setScore(0)
    setAnsweredCount(0)
  }, [questions])

  if (!questions || questions.length === 0) return <p>No questions loaded yet.</p>

  function handleAnswered(isCorrect) {
    setAnsweredCount((c) => c + 1)
    if (isCorrect) setScore((s) => s + 1)
  }

  return (
    <div>
      <p style={{ fontWeight: 'bold' }}>
        Score: {score} / {answeredCount} answered ({questions.length} total)
      </p>
      {questions.map((q, idx) => (
        <QuizQuestion key={idx} q={q} index={idx} onAnswered={handleAnswered} />
      ))}
    </div>
  )
}

export default QuestionList