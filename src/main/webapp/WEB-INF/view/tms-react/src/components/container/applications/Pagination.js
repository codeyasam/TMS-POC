import Pagination from '../../ui/generic/Pagination'
import { setPaginationPageRequest } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    total: state.application.pagination.paginationTotal,
    pageSize: state.application.pagination.paginationSize,
    currentPage: state.application.pagination.paginationPage,
    navSize: state.application.pagination.paginationNavSize
})

const mapDispatchToProps = dispatch => ({
    onSetPaginationPage(page, pageSize) {
        dispatch(setPaginationPageRequest(page, pageSize))
    },
    onSetPaginationNextPage(currentPage, pageSize) {
        dispatch(setPaginationPageRequest(currentPage + 1, pageSize))
    },
    onSetPaginationPreviousPage(currentPage, pageSize) {
        dispatch(setPaginationPageRequest(currentPage - 1, pageSize))
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Pagination)