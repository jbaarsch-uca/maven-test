
import CourseList from './components/CourseList';

function App() {
    return (
        <div className="App">
            <nav style={{ padding: '1rem', background: '#282c34', color: 'white' }}>
                <h1>Student Registration Portal</h1>
            </nav>
            <main>
                <CourseList />
            </main>
        </div>
    );
}

export default App;