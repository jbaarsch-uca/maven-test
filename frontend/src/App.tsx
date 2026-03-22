
import CourseList from './components/CourseList';

function App() {
    console.log("App is rendering!");
    return (
        <div className="App">
            <nav style={{ padding: '1rem', background: '#282c34', color: 'white' }}>
                <h1>Student Registration Portal</h1>
            </nav>
            <p></p>
            <div>
                <CourseList />
            </div>
        </div>
    );
}

export default App;