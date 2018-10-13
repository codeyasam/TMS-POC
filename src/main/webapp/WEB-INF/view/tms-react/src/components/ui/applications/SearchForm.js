import React from 'react'
import { Segment, Button } from 'semantic-ui-react'

const SearchForm = ({ onChangeSearchText=f=>f, currentPage, pageSize }) => {
    
    let _searchInput
    
    const submit = (e) => {
        e.preventDefault()
        _searchInput.value = ''
        onChangeInputValue()
    }
    
    const onChangeInputValue = () => {
        let searchText = _searchInput.value
        onChangeSearchText(searchText, currentPage, pageSize)
    }
    
    return (
        <Segment>
            <form className="fields form ui" onSubmit={submit}>
                <input className="five wide field" 
                    type="text" 
                    placeholder="search..."
                    ref={input => _searchInput = input}
                    onChange={onChangeInputValue} />
                <Button>CLEAR</Button>
            </form>        
        </Segment>
    )
}

export default SearchForm