import { useState } from 'react'
import CategoryPicker from './components/CategoryPicker'
import QuizControls from './components/QuizControls'
import QuestionList from './components/QuestionList'
import { fetchTrivia } from './api/triviaApi'

function App() {
  const [category, setCategory] = useState('')
  const [questions, setQuestions] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  async function handleStart(params) {
    setLoading(true)
    setError(null)
    try {
      const data = await fetchTrivia(params)
      setQuestions(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <h1>Kuriositaeten Trivia</h1>
      <CategoryPicker onCategoryChange={setCategory} />
      <QuizControls category={category} onStart={handleStart} />
      {loading && <p>Loading questions...</p>}
      {error && <p>Error: {error}</p>}
      <QuestionList questions={questions} />
    </div>
  )
}

export default App