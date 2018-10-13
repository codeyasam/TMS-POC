import Pagination from '../../ui/generic/Pagination'
import { setPaginationPageRequest } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    total: state.application.pagination.paginationTotal,
    pageSize: state.application.pagination.paginationSize,
    searchText: state.application.retrieveApplication.applicationSearchText,
    currentPage: state.application.pagination.paginationPage,
    navSize: state.application.pagination.paginationNavSize
})

const mapDispatchToProps = dispatch => ({
    onSetPaginationPage(searchText, page, pageSize) {
        dispatch(setPaginationPageRequest(searchText, page, pageSize))
    },
    onSetPaginationNextPage(searchText, currentPage, pageSize) {
        dispatch(setPaginationPageRequest(searchText, currentPage + 1, pageSize))
    },
    onSetPaginationPreviousPage(searchText, currentPage, pageSize) {
        dispatch(setPaginationPageRequest(searchText, currentPage - 1, pageSize))
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Pagination)