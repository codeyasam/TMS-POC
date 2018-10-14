import SearchForm from '../../ui/applications/SearchForm'
import { fetchApplications, clearSelectedApplicationEntries, setApplicationSearchText } from '../../../actions/applicationActions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    searchText: state.application.retrieveApplication.applicationSearchText,
    currentPage: state.application.pagination.paginationPage,
    pageSize: state.application.pagination.paginationSize
})

const mapDispatchToProps = dispatch => ({
    onChangeSearchText(searchText, currentPage, pageSize) {
        dispatch(setApplicationSearchText(searchText))
        dispatch(fetchApplications(searchText, currentPage, pageSize))
        dispatch(clearSelectedApplicationEntries())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(SearchForm)